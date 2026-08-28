package org.vlaskin.moexiss.service.dictionary;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.BoardGroupResponse;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.DictionaryResponse;
import org.vlaskin.moexiss.entity.DurationResponse;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.entity.SecurityCollectionResponse;
import org.vlaskin.moexiss.entity.SecurityGroupResponse;
import org.vlaskin.moexiss.entity.SecurityTypeResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DictionaryServiceTest
{
    @Test
    void deserializesAllDictionaries() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("dictionaries.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);

        DictionaryResponse dictionaries = client.getDictionaries().getAll();

        assertEquals("https://fixture.test/iss/index.json?iss.meta=on", transport.getRequestedUrl());
        assertEquals("stock", dictionaries.getEngines().getFirst().getName());
        assertEquals("repo", dictionaries.getMarkets().getFirst().getName());
        assertFalse(dictionaries.getMarkets().getFirst()
                .get(MarketResponse.Fields.HAS_CANDLES, Boolean.class));
        assertEquals("TQIF", dictionaries.getBoards().getFirst().getCode());
        assertTrue(dictionaries.getBoards().getFirst()
                .get(BoardResponse.Fields.HAS_CANDLES, Boolean.class));
        assertEquals("stock_index", dictionaries.getBoardGroups().getFirst()
                .get(BoardGroupResponse.Fields.NAME, String.class));
        assertEquals(60, dictionaries.getDurations().getFirst()
                .get(DurationResponse.Fields.DURATION, Integer.class));
        assertNull(dictionaries.getDurations().getFirst()
                .get(DurationResponse.Fields.DAYS, Integer.class));
        assertEquals("common_share", dictionaries.getSecurityTypes().getFirst()
                .get(SecurityTypeResponse.Fields.NAME, String.class));
        assertFalse(dictionaries.getSecurityGroups().getFirst()
                .get(SecurityGroupResponse.Fields.IS_HIDDEN, Boolean.class));
        assertEquals(12, dictionaries.getSecurityCollections().getFirst()
                .get(SecurityCollectionResponse.Fields.SECURITY_GROUP_ID, Integer.class));
    }

    @Test
    void requestsIndividualDictionariesThroughIssOnly() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("dictionaries.json");
        DictionaryService service = new DictionaryService("https://fixture.test", transport);

        assertEquals(1, service.getEngines().size());
        assertEquals(1, service.getMarkets().size());
        assertEquals(1, service.getBoards().size());
        assertEquals(1, service.getBoardGroups().size());
        assertEquals(1, service.getDurations().size());
        assertEquals(1, service.getSecurityTypes().size());
        assertEquals(1, service.getSecurityGroups().size());
        assertEquals(1, service.getSecurityCollections().size());
        assertEquals("https://fixture.test/iss/index.json?iss.meta=on"
                        + "&iss.only=securitycollections",
                transport.getRequestedUrl());
    }
}
