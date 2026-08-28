package org.vlaskin.moexiss;

import org.vlaskin.moexiss.http.MoexHttpTransport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class FixtureTransport implements MoexHttpTransport
{
    private final String fixture;
    private String requestedUrl;

    public FixtureTransport(String fixture)
    {
        this.fixture = fixture;
    }

    @Override
    public String get(String url) throws IOException
    {
        requestedUrl = url;
        try (InputStream input = FixtureTransport.class.getResourceAsStream("/responses/" + fixture))
        {
            if (input == null)
                throw new IOException("Fixture not found: " + fixture);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public String getRequestedUrl()
    {
        return requestedUrl;
    }
}
