package org.vlaskin.moexiss.service.engine;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.entity.DailyTableRecordResponse;
import org.vlaskin.moexiss.entity.DataVersionResponse;
import org.vlaskin.moexiss.entity.EngineInfoResponse;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.entity.MarketDataResponse;
import org.vlaskin.moexiss.entity.MarketDataYieldResponse;
import org.vlaskin.moexiss.entity.MarketInfoResponse;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.entity.SecuritiesTableResponse;
import org.vlaskin.moexiss.entity.SecurityTableResponse;
import org.vlaskin.moexiss.entity.TimeTableRecordResponse;
import org.vlaskin.moexiss.service.engine.params.BoardEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardsEngineParams;
import org.vlaskin.moexiss.service.engine.params.InfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketInfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketsEngineParams;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EngineServiceTest
{
    @Test
    void deserializesEngineDescriptionAndSchedules() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("engine-info.json");
        EngineService service = new EngineService("https://fixture.test", transport);
        InfoEngineParams params = new InfoEngineParams("stock");

        EngineInfoResponse result = service.getInfo(params);

        assertEquals("stock", result.getEngine().getName());
        assertEquals(1, result.getTimeTable().size());
        assertEquals(1, result.getDailyTable().size());
        assertEquals(LocalTime.of(6, 20), result.getTimeTable().getFirst()
                .get(TimeTableRecordResponse.Fields.START_TIME, LocalTime.class));
        assertEquals(LocalDate.of(2018, 5, 9), result.getDailyTable().getFirst()
                .get(DailyTableRecordResponse.Fields.DATE, LocalDate.class));

        assertEquals("stock", service.getEngineInfo(params).orElseThrow().getName());
        assertEquals(1, service.getTimeTableInfo(params).size());
        assertEquals(1, service.getDailyTableInfo(params).size());
        assertEquals("https://fixture.test/iss/engines/stock.json"
                        + "?iss.meta=on&iss.only=dailytable&lang=ru",
                transport.getRequestedUrl());
    }

    @Test
    void deserializesMarketsAndAllMarketMetadataSections() throws IOException
    {
        FixtureTransport marketsTransport = new FixtureTransport("markets.json");
        EngineService marketsService = new EngineService("https://fixture.test", marketsTransport);

        List<MarketResponse> markets = marketsService.getMarkets(new MarketsEngineParams("stock"));

        assertEquals(1, markets.size());
        assertEquals("shares", markets.getFirst().getName());
        assertEquals("https://fixture.test/iss/engines/stock/markets.json?iss.meta=on&lang=ru",
                marketsTransport.getRequestedUrl());

        FixtureTransport metadataTransport = new FixtureTransport("market-info.json");
        EngineService metadataService = new EngineService("https://fixture.test", metadataTransport);
        MarketInfoEngineParams params = new MarketInfoEngineParams("stock", "shares");
        MarketInfoResponse info = metadataService.getMarketInfo(params);

        assertEquals(1, info.getBoards().size());
        assertEquals(1, info.getBoardGroups().size());
        assertEquals(1, info.getSecurityFields().size());
        assertEquals(1, info.getMarketDataFields().size());
        assertEquals(1, info.getTradeFields().size());
        assertEquals(1, info.getOrderBookFields().size());
        assertEquals(1, info.getHistoryFields().size());
        assertEquals(1, info.getTradeHistoryFields().size());
        assertTrue(info.getMarketDataYieldFields().isEmpty());
        assertTrue(info.getTradeYieldFields().isEmpty());
        assertTrue(info.getHistoryYieldFields().isEmpty());
        assertEquals(1, info.getSecurityStatisticFields().size());

        assertEquals(1, metadataService.getMarketBoards(params).size());
        assertEquals(1, metadataService.getMarketBoardGroups(params).size());
        assertEquals(1, metadataService.getMarketSecurityFields(params).size());
        assertEquals(1, metadataService.getMarketDataFields(params).size());
        assertEquals(1, metadataService.getMarketTradeFields(params).size());
        assertEquals(1, metadataService.getMarketOrderBookFields(params).size());
        assertEquals(1, metadataService.getMarketHistoryFields(params).size());
        assertEquals(1, metadataService.getMarketTradeHistoryFields(params).size());
        assertTrue(metadataService.getMarketDataYieldFields(params).isEmpty());
        assertTrue(metadataService.getMarketTradeYieldFields(params).isEmpty());
        assertTrue(metadataService.getMarketHistoryYieldFields(params).isEmpty());
        assertEquals(1, metadataService.getMarketSecurityStatisticFields(params).size());
        assertEquals("https://fixture.test/iss/engines/stock/markets/shares.json"
                        + "?iss.meta=on&iss.only=secstats&lang=ru",
                metadataTransport.getRequestedUrl());
    }

    @Test
    void deserializesBoardsAndBoardDescription() throws IOException
    {
        FixtureTransport boardsTransport = new FixtureTransport("market-info.json");
        EngineService boardsService = new EngineService("https://fixture.test", boardsTransport);

        assertEquals("EQBR", boardsService.getBoards(new BoardsEngineParams("stock", "shares"))
                .getFirst().getCode());

        FixtureTransport boardTransport = new FixtureTransport("board.json");
        EngineService boardService = new EngineService("https://fixture.test", boardTransport);
        assertEquals("TQBR", boardService.getBoard(new BoardEngineParams("stock", "shares", "TQBR"))
                .orElseThrow().getCode());
        assertEquals("https://fixture.test/iss/engines/stock/markets/shares/boards/TQBR.json"
                        + "?iss.meta=on&lang=ru",
                boardTransport.getRequestedUrl());
    }

    @Test
    void deserializesEverySecuritiesTableVariant() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("market-tables.json");
        EngineService service = new EngineService("https://fixture.test", transport);

        MarketSecuritiesTableEngineParams marketParams =
                new MarketSecuritiesTableEngineParams("stock", "shares");
        assertCompleteTable(service.getMarketSecuritiesTable(marketParams));
        assertEquals(1, service.getMarketSecurities(marketParams).size());
        assertEquals(1, service.getMarketData(marketParams).size());
        assertEquals(1, service.getMarketDataVersions(marketParams).size());
        assertTrue(service.getMarketDataYields(marketParams).isEmpty());

        MarketSecurityTableEngineParams marketSecurityParams =
                new MarketSecurityTableEngineParams("stock", "shares", "SBER");
        assertCompleteTable(service.getMarketSecuritiesTable(marketSecurityParams));
        assertEquals(1, service.getMarketSecurities(marketSecurityParams).size());
        assertEquals(1, service.getMarketData(marketSecurityParams).size());
        assertEquals(1, service.getMarketDataVersions(marketSecurityParams).size());
        assertTrue(service.getMarketDataYields(marketSecurityParams).isEmpty());

        BoardSecuritiesTableEngineParams boardParams =
                new BoardSecuritiesTableEngineParams("stock", "shares", "TQBR");
        assertCompleteTable(service.getBoardSecuritiesTable(boardParams));
        assertEquals(1, service.getBoardSecurities(boardParams).size());
        assertEquals(1, service.getBoardMarketData(boardParams).size());
        assertEquals(1, service.getBoardDataVersions(boardParams).size());
        assertTrue(service.getBoardMarketDataYields(boardParams).isEmpty());

        BoardSecurityTableEngineParams boardSecurityParams =
                new BoardSecurityTableEngineParams("stock", "shares", "TQBR", "SBER");
        SecurityTableResponse securityTable = service.getBoardSecurityTable(boardSecurityParams);
        assertNotNull(securityTable.getSecurity());
        assertEquals("SBER", securityTable.getSecurity().getCode());
        assertEquals(1, securityTable.getMarketData().size());
        assertEquals(1, securityTable.getDataVersions().size());
        assertTrue(securityTable.getMarketDataYields().isEmpty());
        assertEquals("SBER", service.getBoardSecurity(boardSecurityParams).orElseThrow().getCode());
        assertEquals(1, service.getBoardMarketData(boardSecurityParams).size());
        assertEquals(1, service.getBoardDataVersions(boardSecurityParams).size());
        assertTrue(service.getBoardMarketDataYields(boardSecurityParams).isEmpty());
        assertEquals("https://fixture.test/iss/engines/stock/markets/shares/boards/TQBR/securities/SBER.json"
                        + "?iss.meta=on&iss.only=marketdata_yields&lang=ru&first=0"
                        + "&sort_order=asc&leaders=0&nearest=0&previous_session=0",
                transport.getRequestedUrl());
    }

    @Test
    void parsesMarketDataYieldFromRealResponse() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("marketdata-yields.json");
        EngineService service = new EngineService("https://fixture.test", transport);
        BoardSecuritiesTableEngineParams params =
                new BoardSecuritiesTableEngineParams("stock", "bonds", "TQCB");

        List<MarketDataYieldResponse> result = service.getBoardMarketDataYields(params);

        assertEquals(1, result.size());
        assertEquals("BYM000001818",
                result.getFirst().get(MarketDataYieldResponse.Fields.SECURITY_CODE, String.class));
        assertEquals(LocalDate.of(2027, 6, 29),
                result.getFirst().get(MarketDataYieldResponse.Fields.YIELD_DATE, LocalDate.class));
    }

    @Test
    void encodesPathAndSecuritiesTableParameters() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        EngineService service = new EngineService("https://fixture.test", transport);
        MarketSecuritiesTableEngineParams params =
                new MarketSecuritiesTableEngineParams("stock/value", "shares market");
        params.setSortColumn("SEC ID");
        params.setPrimaryBoard("TQ&BR");
        params.setAssets(List.of("stock +", "currency&"));
        params.setIndex("IMO/EX");
        params.setSecurities(List.of("S BER", "GAZP&LKOH"));
        params.setSecurityTypes(List.of("common/share"));
        params.setSecurityCollection("stock#shares");

        service.getMarketSecurities(params);

        assertEquals("https://fixture.test/iss/engines/stock%2Fvalue/markets/shares%20market/securities.json"
                + "?iss.meta=on&iss.only=securities&lang=ru&first=0&sort_column=SEC+ID&sort_order=asc"
                + "&leaders=0&nearest=0&previous_session=0&primary_board=TQ%26BR"
                + "&assets=stock+%2B,currency%26&index=IMO%2FEX"
                + "&securities=S+BER,GAZP%26LKOH&sectypes=common%2Fshare"
                + "&security_collection=stock%23shares",
                transport.getRequestedUrl());
    }

    @Test
    void rejectsNegativeStartBeforeRequest()
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        EngineService service = new EngineService("https://fixture.test", transport);
        MarketSecuritiesTableEngineParams params =
                new MarketSecuritiesTableEngineParams("stock", "shares");
        params.setFirst(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getMarketSecurities(params));

        assertEquals("Parameter first must not be negative",
                exception.getMessage());
        assertNull(transport.getRequestedUrl());
    }

    private static void assertCompleteTable(SecuritiesTableResponse table)
    {
        assertEquals("SBER", table.getSecurities().getFirst().getCode());
        assertEquals(277.04, table.getMarketData().getFirst()
                .get(MarketDataResponse.Fields.LAST, Double.class));
        assertEquals(8949, table.getDataVersions().getFirst()
                .get(DataVersionResponse.Fields.DATA_VERSION, Integer.class));
        assertFalse(table.getSecurities().isEmpty());
        assertTrue(table.getMarketDataYields().isEmpty());
    }
}
