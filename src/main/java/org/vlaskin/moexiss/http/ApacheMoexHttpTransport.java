package org.vlaskin.moexiss.http;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ApacheMoexHttpTransport implements MoexHttpTransport
{
    private static final Timeout CONNECT_TIMEOUT = Timeout.ofSeconds(10);
    private static final Timeout RESPONSE_TIMEOUT = Timeout.ofSeconds(30);

    @Override
    public String get(String url) throws IOException
    {
        return Request.get(url)
                .connectTimeout(CONNECT_TIMEOUT)
                .responseTimeout(RESPONSE_TIMEOUT)
                .execute()
                .returnContent()
                .asString(StandardCharsets.UTF_8);
    }
}
