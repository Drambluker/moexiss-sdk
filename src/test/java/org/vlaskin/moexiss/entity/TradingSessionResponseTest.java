package org.vlaskin.moexiss.entity;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class TradingSessionResponseTest
{
    @Test
    void returnsNullForMissingTradingSession()
    {
        assertNull(new IndexAnalyticsDataResponse.Processor().processJsonElement(
                JsonParser.parseString("[null]"),
                JsonParser.parseString("[\"tradingsession\"]").getAsJsonArray(),
                JsonParser.parseString("{\"tradingsession\":{\"type\":\"int32\"}}")
                        .getAsJsonObject()).getTradingSession());

        assertNull(new TickerResponse.Processor().processJsonElement(
                JsonParser.parseString("[null]"),
                JsonParser.parseString("[\"tradingsession\"]").getAsJsonArray(),
                JsonParser.parseString("{\"tradingsession\":{\"type\":\"int32\"}}")
                        .getAsJsonObject()).getTradingSession());
    }
}
