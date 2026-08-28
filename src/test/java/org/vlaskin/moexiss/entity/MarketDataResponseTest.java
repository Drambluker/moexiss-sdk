package org.vlaskin.moexiss.entity;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarketDataResponseTest
{
    @Test
    void safelyConvertsInt64ToInteger()
    {
        MarketDataResponse response = processBidDepth("42");

        assertEquals(42, response.get(MarketDataResponse.Fields.BID_DEPTH_T, Integer.class));
    }

    @Test
    void rejectsInt64OutsideIntegerRange()
    {
        assertThrows(ArithmeticException.class, () -> processBidDepth("2147483648"));
    }

    private static MarketDataResponse processBidDepth(String value)
    {
        return new MarketDataResponse.Processor().processJsonElement(
                JsonParser.parseString("[" + value + "]"),
                JsonParser.parseString("[\"BIDDEPTHT\"]").getAsJsonArray(),
                JsonParser.parseString("{\"BIDDEPTHT\":{\"type\":\"int64\"}}").getAsJsonObject());
    }
}
