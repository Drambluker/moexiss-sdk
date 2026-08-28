package org.vlaskin.moexiss.service;

import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.response.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseServiceTest
{
    @Test
    void encodesQueryValueAndPathSegmentDifferently()
    {
        String value = "тест +&/#";
        StringBuilder request = new StringBuilder("?iss.meta=on");

        TestService.appendParameter(request, "value", value);

        assertEquals("?iss.meta=on&value=%D1%82%D0%B5%D1%81%D1%82+%2B%26%2F%23",
                request.toString());
        assertEquals("%D1%82%D0%B5%D1%81%D1%82%20%2B%26%2F%23",
                TestService.pathSegment(value));
    }

    @Test
    void keepsCollectionSeparatorsAndEncodesIndividualValues()
    {
        StringBuilder request = new StringBuilder("?iss.meta=on");

        TestService.appendParameters(request, "securities",
                List.of("S BER", "GAZP&LKOH", "A,B"));

        assertEquals("?iss.meta=on&securities=S+BER,GAZP%26LKOH,A%2CB",
                request.toString());
    }

    @Test
    void validatesPaginationAndOverflow()
    {
        assertEquals(100, TestService.startIndex(2, 50));
        assertThrows(IllegalArgumentException.class,
                () -> TestService.startIndex(-1, 50));
        assertThrows(IllegalArgumentException.class,
                () -> TestService.startIndex(1, 0));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> TestService.startIndex(Integer.MAX_VALUE, 2));
        assertEquals("Product of pageIndex and limit exceeds the supported range",
                exception.getMessage());
    }

    @Test
    void rejectsEmptyResponse()
    {
        TestService service = new TestService();

        IOException blank = assertThrows(IOException.class, () -> service.parse("  "));
        IOException jsonNull = assertThrows(IOException.class, () -> service.parse("null"));

        assertEquals("MOEX ISS returned an empty response", blank.getMessage());
        assertEquals("MOEX ISS returned an empty response", jsonNull.getMessage());
    }

    @Test
    void convertsNullTransportResponseToIOException()
    {
        TestService service = new TestService();

        IOException exception = assertThrows(IOException.class, service::requestAndParse);

        assertEquals("MOEX ISS returned an empty response", exception.getMessage());
    }

    @Test
    void wrapsMalformedResponseWithoutIncludingBody()
    {
        TestService service = new TestService();

        IOException exception = assertThrows(IOException.class,
                () -> service.parse("{\"token\":\"secret\""));

        assertEquals("Failed to parse MOEX ISS response", exception.getMessage());
        assertTrue(exception.getCause() instanceof JsonParseException);
        assertFalse(exception.getMessage().contains("secret"));
    }

    private static final class TestService extends BaseService
    {
        private TestService()
        {
            super("https://fixture.test", url -> null);
        }

        private static void appendParameter(StringBuilder request, String name, Object value)
        {
            appendQueryParameter(request, name, value);
        }

        private static void appendParameters(StringBuilder request, String name, List<?> values)
        {
            appendQueryParameter(request, name, values);
        }

        private static String pathSegment(Object value)
        {
            return encodePathSegment(value);
        }

        private static int startIndex(int pageIndex, int limit)
        {
            return calculateStartIndex(pageIndex, limit);
        }

        private Response parse(String response) throws IOException
        {
            return parseResponse(response, Response.class);
        }

        private Response requestAndParse() throws IOException
        {
            return getAndParseResponse("https://fixture.test/iss/index.json", Response.class);
        }
    }
}
