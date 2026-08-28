package org.vlaskin.moexiss.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApacheMoexHttpTransportTest
{
    private HttpServer server;
    private String baseUrl;

    @BeforeEach
    void startServer() throws IOException
    {
        server = HttpServer.create(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0), 0);
        server.start();
        baseUrl = "http://" + server.getAddress().getHostString() + ':' + server.getAddress().getPort();
    }

    @AfterEach
    void stopServer()
    {
        if (server != null)
            server.stop(0);
    }

    @Test
    void returnsUtf8Response() throws IOException
    {
        server.createContext("/success", exchange -> respond(exchange, 200, "{\"name\":\"MOEX ✓\"}"));

        String response = new ApacheMoexHttpTransport().get(baseUrl + "/success");

        assertEquals("{\"name\":\"MOEX ✓\"}", response);
    }

    @Test
    void returnsEmptySuccessfulResponse() throws IOException
    {
        server.createContext("/empty", exchange -> respond(exchange, 200, ""));

        String response = new ApacheMoexHttpTransport().get(baseUrl + "/empty");

        assertTrue(response.isEmpty());
    }

    @Test
    void exposesStatusAndRedactsQueryForHttpError()
    {
        server.createContext("/unavailable", exchange -> respond(exchange, 503, "temporarily unavailable"));

        MoexHttpException exception = assertThrows(MoexHttpException.class,
                () -> new ApacheMoexHttpTransport().get(baseUrl + "/unavailable?token=secret"));

        assertEquals(503, exception.getStatusCode());
        assertEquals(baseUrl + "/unavailable", exception.getRequestTarget());
        assertFalse(exception.getMessage().contains("token"));
        assertFalse(exception.getMessage().contains("secret"));
    }

    @Test
    void reportsResponseTimeoutWithoutQuery()
    {
        AtomicInteger requestCount = new AtomicInteger();
        server.createContext("/slow", exchange -> {
            requestCount.incrementAndGet();
            try
            {
                Thread.sleep(500);
                respond(exchange, 200, "late");
            }
            catch (InterruptedException exception)
            {
                Thread.currentThread().interrupt();
            }
        });
        ApacheMoexHttpTransport transport = new ApacheMoexHttpTransport(
                Duration.ofSeconds(1), Duration.ofMillis(100));

        IOException exception = assertThrows(IOException.class,
                () -> transport.get(baseUrl + "/slow?token=secret"));

        assertTrue(exception.getMessage().contains(baseUrl + "/slow"));
        assertFalse(exception.getMessage().contains("secret"));
        assertEquals(1, requestCount.get());
    }

    @Test
    void rejectsInvalidTimeouts()
    {
        assertThrows(NullPointerException.class,
                () -> new ApacheMoexHttpTransport(null, Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new ApacheMoexHttpTransport(Duration.ZERO, Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new ApacheMoexHttpTransport(Duration.ofSeconds(1), Duration.ofNanos(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new ApacheMoexHttpTransport(Duration.ofSeconds(Long.MAX_VALUE), Duration.ofSeconds(1)));
    }

    @Test
    void rejectsInvalidUrl()
    {
        ApacheMoexHttpTransport transport = new ApacheMoexHttpTransport();

        IOException exception = assertThrows(IOException.class, () -> transport.get("not-a-url"));

        assertEquals("Invalid request URL", exception.getMessage());
    }

    private static void respond(HttpExchange exchange, int statusCode, String body) throws IOException
    {
        byte[] content = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, content.length);
        exchange.getResponseBody().write(content);
        exchange.close();
    }
}
