package org.vlaskin.moexiss.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseDeserializerTest
{
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Response.class, new ResponseDeserializer())
            .create();

    @Test
    void rejectsNonObjectRoot()
    {
        JsonParseException exception = assertThrows(JsonParseException.class,
                () -> gson.fromJson("[]", Response.class));

        assertTrue(exception.getMessage().contains("response root"));
    }

    @Test
    void rejectsMissingMetadataForKnownSection()
    {
        JsonParseException exception = assertThrows(JsonParseException.class,
                () -> gson.fromJson("{\"securities\":{\"columns\":[],\"data\":[]}}", Response.class));

        assertTrue(exception.getMessage().contains("metadata"));
        assertTrue(exception.getMessage().contains("securities"));
    }

    @Test
    void rejectsMissingColumnMetadata()
    {
        JsonParseException exception = assertThrows(JsonParseException.class,
                () -> gson.fromJson("""
                        {"securities":{"metadata":{},"columns":["SECID"],"data":[]}}
                        """, Response.class));

        assertTrue(exception.getMessage().contains("SECID"));
        assertTrue(exception.getMessage().contains("Metadata"));
    }

    @Test
    void rejectsRowWithUnexpectedNumberOfValues()
    {
        JsonParseException exception = assertThrows(JsonParseException.class,
                () -> gson.fromJson("""
                        {"securities":{"metadata":{"SECID":{"type":"string"}},
                        "columns":["SECID"],"data":[[]]}}
                        """, Response.class));

        assertTrue(exception.getMessage().contains("Row 1"));
        assertTrue(exception.getMessage().contains("1 columns"));
    }

    @Test
    void ignoresUnknownSections()
    {
        assertDoesNotThrow(() -> gson.fromJson("{\"future\":null}", Response.class));
    }
}
