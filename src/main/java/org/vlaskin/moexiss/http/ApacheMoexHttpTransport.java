package org.vlaskin.moexiss.http;

import org.apache.hc.client5.http.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ApacheMoexHttpTransport implements MoexHttpTransport
{
    @Override
    public String get(String url) throws IOException
    {
        return Request.get(url).execute().returnContent().asString(StandardCharsets.UTF_8);
    }
}
