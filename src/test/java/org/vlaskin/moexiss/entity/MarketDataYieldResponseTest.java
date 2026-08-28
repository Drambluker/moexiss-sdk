package org.vlaskin.moexiss.entity;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarketDataYieldResponseTest
{
    @Test
    void convertsDateTimeValueDeclaredAsDate()
    {
        MarketDataYieldResponse response = new MarketDataYieldResponse.Processor().processJsonElement(
                JsonParser.parseString("[\"2027-06-29 12:34:56\"]"),
                JsonParser.parseString("[\"YIELDDATE\"]").getAsJsonArray(),
                JsonParser.parseString("{\"YIELDDATE\":{\"type\":\"date\"}}").getAsJsonObject());

        assertEquals(LocalDate.of(2027, 6, 29),
                response.get(MarketDataYieldResponse.Fields.YIELD_DATE, LocalDate.class));
    }
}
