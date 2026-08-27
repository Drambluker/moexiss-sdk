package org.vlaskin.moexiss;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.service.engine.params.ListEngineParams;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoexClientTest
{
    @Test
    void usesConfiguredBaseUrlAndDeserializesResponse() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("engines.json");
        MoexClient client = new MoexClient("https://fixture.test/", transport);

        List<EngineResponse> engines = client.getEngines().getList(new ListEngineParams());

        assertEquals("https://fixture.test/iss/engines.json?iss.meta=on&lang=ru", transport.getRequestedUrl());
        assertEquals(1, engines.size());
        assertEquals(1, engines.getFirst().getIntegerFields().get(EngineResponse.Fields.ID));
        assertEquals("stock", engines.getFirst().getStringFields().get(EngineResponse.Fields.NAME));
    }

    @Test
    void propagatesTransportFailure()
    {
        IOException failure = new IOException("MOEX is unavailable");
        MoexClient client = new MoexClient("https://fixture.test", url -> {
            throw failure;
        });

        IOException thrown = assertThrows(IOException.class,
                () -> client.getEngines().getList(new ListEngineParams()));

        assertTrue(thrown == failure);
    }

    @Test
    void rejectsInvalidConfiguration()
    {
        assertThrows(IllegalArgumentException.class, () -> new MoexClient("///", url -> ""));
        assertThrows(NullPointerException.class, () -> new MoexClient("https://fixture.test", null));
    }
}
