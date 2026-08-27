package org.vlaskin.moexiss.http;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

/** Реализация транспорта на базе Apache HttpComponents с настраиваемыми тайм-аутами. */
public final class ApacheMoexHttpTransport implements MoexHttpTransport
{
    /** Тайм-аут установления соединения по умолчанию. */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(10);
    /** Тайм-аут ожидания ответа по умолчанию. */
    public static final Duration DEFAULT_RESPONSE_TIMEOUT = Duration.ofSeconds(30);

    private final Timeout connectTimeout;
    private final Timeout responseTimeout;

    /** Создаёт транспорт со стандартными тайм-аутами. */
    public ApacheMoexHttpTransport()
    {
        this(DEFAULT_CONNECT_TIMEOUT, DEFAULT_RESPONSE_TIMEOUT);
    }

    /**
     * Создаёт транспорт с заданными тайм-аутами.
     *
     * @param connectTimeout тайм-аут установления соединения, не меньше 1 мс
     * @param responseTimeout тайм-аут ожидания ответа, не меньше 1 мс
     * @throws NullPointerException если один из тайм-аутов равен {@code null}
     * @throws IllegalArgumentException если тайм-аут неположительный или слишком велик
     */
    public ApacheMoexHttpTransport(Duration connectTimeout, Duration responseTimeout)
    {
        this.connectTimeout = toTimeout(connectTimeout, "Connect timeout");
        this.responseTimeout = toTimeout(responseTimeout, "Response timeout");
    }

    @Override
    public String get(String url) throws IOException
    {
        URI requestUri = parseRequestUri(url);
        String requestTarget = toSafeRequestTarget(requestUri);
        try
        {
            return Request.get(requestUri)
                    .connectTimeout(connectTimeout)
                    .responseTimeout(responseTimeout)
                    .execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
        }
        catch (HttpResponseException exception)
        {
            throw new MoexHttpException(exception.getStatusCode(), requestTarget, exception);
        }
        catch (IOException exception)
        {
            throw new IOException("MOEX ISS request failed for " + requestTarget + ": "
                    + exception.getMessage(), exception);
        }
    }

    private static Timeout toTimeout(Duration duration, String name)
    {
        Objects.requireNonNull(duration, name + " must not be null");
        long milliseconds;
        try
        {
            milliseconds = duration.toMillis();
        }
        catch (ArithmeticException exception)
        {
            throw new IllegalArgumentException(name + " is too large", exception);
        }
        if (duration.isNegative() || duration.isZero() || milliseconds == 0)
            throw new IllegalArgumentException(name + " must be at least 1 ms");
        return Timeout.ofMilliseconds(milliseconds);
    }

    private static URI parseRequestUri(String url) throws IOException
    {
        if (url == null)
            throw new IOException("Invalid request URL");
        try
        {
            URI uri = URI.create(url);
            if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                    || uri.getHost() == null)
                throw new IllegalArgumentException();
            return uri;
        }
        catch (IllegalArgumentException exception)
        {
            throw new IOException("Invalid request URL", exception);
        }
    }

    private static String toSafeRequestTarget(URI uri)
    {
        StringBuilder target = new StringBuilder(uri.getScheme()).append("://").append(uri.getHost());
        if (uri.getPort() >= 0)
            target.append(':').append(uri.getPort());
        if (uri.getRawPath() == null || uri.getRawPath().isEmpty())
            return target.append('/').toString();
        return target.append(uri.getRawPath()).toString();
    }
}
