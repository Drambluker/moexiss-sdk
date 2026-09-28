package org.vlaskin.moexiss.service.history;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceTest
{
    private final LocalDate from = LocalDate.of(2025, 9, 22);
    private final LocalDate to = LocalDate.of(2025, 9, 26);

    @Test
    void readsRealIndexHistoryWithValidatedCursor() throws Exception
    {
        var transport = new FixtureTransport("history-index.json");
        var page = new MoexClient("https://example.test", transport).getHistory()
                .getIndexHistory(new HistoryQuery("stock", "index", null, "IMOEX", from, to, 0));
        assertEquals(5, page.rows().size());
        assertEquals(new BigDecimal("2741.06"), page.rows().getFirst().close());
        assertEquals("SNDX", page.rows().getFirst().boardCode());
        assertTrue(page.complete());
        assertEquals(5, page.nextOffset());
        assertTrue(transport.getRequestedUrl().contains("from=2025-09-22"));
        assertThrows(UnsupportedOperationException.class, () -> page.rows().clear());
    }

    @Test
    void retainsHistoricalBondNominalAndAccruedInterest() throws Exception
    {
        var page = new MoexClient("https://example.test", new FixtureTransport("history-bond.json"))
                .getHistory().getSecurityHistory(
                        new HistoryQuery("stock", "bonds", "TQOB", "SU26238RMFS4", from, to, 0));
        var row = page.rows().getFirst();
        assertEquals(new BigDecimal("58.54"), row.close());
        assertEquals(new BigDecimal("1000"), row.faceValue());
        assertEquals(new BigDecimal("21.4"), row.accruedInterest());
        assertEquals("SUR", row.currencyCode());
        assertEquals("RUB", row.faceCurrencyCode());
    }

    @Test
    void rejectsMissingHistoryAndNonAdvancingCursor()
    {
        var query = new HistoryQuery("stock", "shares", "TQBR", "SBER", from, to, 0);
        var missing = new MoexClient("https://example.test", url -> "{\"description\":{}}");
        assertThrows(IOException.class, () -> missing.getHistory().getSecurityHistory(query));
        String stalled = """
                {"history":{"columns":["SECID","BOARDID","TRADEDATE","CLOSE","CURRENCYID"],"data":[]},
                 "history.cursor":{"columns":["INDEX","TOTAL","PAGESIZE"],"data":[[0,100,100]]}}
                """;
        var client = new MoexClient("https://example.test", url -> stalled);
        assertThrows(IOException.class, () -> client.getHistory().getSecurityHistory(query));
    }

    @Test
    void distinguishesUnavailablePaymentsFromConfirmedEmptySplits() throws Exception
    {
        var service = new MoexClient("https://example.test", new FixtureTransport("history-splits.json")).getHistory();
        assertEquals(HistoryAvailability.UNSUPPORTED, service.getDistributions("SBER").availability());
        var splits = service.getCorporateActions("SBER");
        assertEquals(HistoryAvailability.SUPPORTED, splits.availability());
        assertTrue(splits.rows().isEmpty());
        assertFalse(splits.complete());
        var gmkn = new MoexClient("https://example.test", new FixtureTransport("history-splitsGmkn.json"))
                .getHistory().getCorporateActions("GMKN").rows().getFirst();
        assertEquals(new BigDecimal("100"), gmkn.after());
        assertEquals(BigDecimal.ONE, gmkn.before());
    }

    @Test
    void supportsSuccessivePagesAndIgnoresUnknownColumns() throws Exception
    {
        String json = """
                {"history":{"columns":["TRADEDATE","SECID","BOARDID","CLOSE","CURRENCYID","TRADINGSESSION","NEW_FIELD"],
                 "data":[["2025-09-22","SBER","TQBR",100,"SUR",3,"ignored"]]},
                 "history.cursor":{"columns":["TOTAL","PAGESIZE","INDEX"],"data":[[2,1,0]]}}
                """;
        var service = new MoexClient("https://example.test", url -> json).getHistory();
        var page = service.getSecurityHistory(new HistoryQuery("stock", "shares", "TQBR", "SBER", from, to, 0));
        assertFalse(page.complete());
        assertEquals(1, page.nextOffset());
        String second = json.replace("[2,1,0]", "[2,1,1]");
        var finalPage = new MoexClient("https://example.test", url -> second).getHistory()
                .getSecurityHistory(new HistoryQuery("stock", "shares", "TQBR", "SBER", from, to, page.nextOffset()));
        assertTrue(finalPage.complete());
    }

    @Test
    void nullPriceIsNotSilentlyReplacedByZero() throws Exception
    {
        String json = """
                {"history":{"columns":["SECID","BOARDID","TRADEDATE","CLOSE","CURRENCYID"],
                 "data":[["SBER","TQBR","2025-09-22",null,"SUR"]]},
                 "history.cursor":{"columns":["INDEX","TOTAL","PAGESIZE"],"data":[[0,1,100]]}}
                """;
        var query = new HistoryQuery("stock", "shares", "TQBR", "SBER", from, to, 0);
        assertNull(new MoexClient("https://example.test", url -> json).getHistory()
                .getSecurityHistory(query).rows().getFirst().close());
        assertThrows(IOException.class, () -> new MoexClient("https://example.test",
                url -> json.replace("\"CLOSE\"", "\"UNKNOWN\"")).getHistory().getSecurityHistory(query));
        assertThrows(IOException.class, () -> new MoexClient("https://example.test",
                url -> json.replace("TQBR", "TQTF")).getHistory().getSecurityHistory(query));
    }

    @Test
    void rejectsInvalidSchemaEvenWhenHistoryIsEmpty()
    {
        String json = """
                {"history":{"columns":["SECID","BOARDID","TRADEDATE","CLOSE","CURRENCYID"],"data":[]},
                 "history.cursor":{"columns":["INDEX","TOTAL","PAGESIZE"],"data":[[0,0,100]]}}
                """;
        var query = new HistoryQuery("stock", "shares", "TQBR", "SBER", from, to, 0);
        assertThrows(IOException.class, () -> new MoexClient("https://example.test",
                url -> json.replace("\"CLOSE\"", "\"UNKNOWN\"")).getHistory().getSecurityHistory(query));
        assertThrows(IOException.class, () -> new MoexClient("https://example.test",
                url -> json.replace("\"CLOSE\"", "\"SECID\"")).getHistory().getSecurityHistory(query));
    }

    @Test
    void rejectsContradictoryPublicPageAndCoverage()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new HistoryPage<>(java.util.List.of(), 0, 1, 100, true));
        assertThrows(IllegalArgumentException.class,
                () -> new HistoryDataset<>(HistoryAvailability.UNSUPPORTED, java.util.List.of(), true, "unavailable"));
    }
}
