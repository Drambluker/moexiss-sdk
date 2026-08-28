package org.vlaskin.moexiss.entity;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityResponseTest
{
    @Test
    void parsesSupportedBooleanCodes()
    {
        assertEquals(Boolean.FALSE, processTradingFlag("0").getTraded());
        assertEquals(Boolean.TRUE, processTradingFlag("1").getTraded());
    }

    @Test
    void rejectsUnsupportedBooleanCode()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> processTradingFlag("2"));

        assertEquals("Boolean field 'IS_TRADED' contains unsupported value 2",
                exception.getMessage());
    }

    private static SecurityResponse processTradingFlag(String value)
    {
        return new SecurityResponse.Processor().processJsonElement(
                JsonParser.parseString("[" + value + "]"),
                JsonParser.parseString("[\"is_traded\"]").getAsJsonArray(),
                JsonParser.parseString("{\"is_traded\":{\"type\":\"int32\"}}")
                        .getAsJsonObject());
    }
}
