package org.vlaskin.moexiss.service.engine;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.service.TestUtils;
import org.vlaskin.moexiss.service.engine.params.BoardEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardsEngineParams;
import org.vlaskin.moexiss.service.engine.params.InfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.ListEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketInfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketsEngineParams;

import java.io.IOException;

// TODO
@Slf4j
class EngineServiceTest
{
    private static final MoexClient client = new MoexClient();
    private static final EngineService service = client.getEngines();

    private static final ListEngineParams listParams = new ListEngineParams();
    private static final InfoEngineParams infoParams = new InfoEngineParams(TestUtils.ENGINE);
    private static final MarketsEngineParams marketsParams = new MarketsEngineParams(TestUtils.ENGINE);
    private static final MarketSecurityTableEngineParams marketSecurityTableParams = new MarketSecurityTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.SECURITY);
    private static final BoardEngineParams boardParams = new BoardEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD);
    private static final BoardSecuritiesTableEngineParams boardSecuritiesTableParams = new BoardSecuritiesTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD);
    private static final BoardSecurityTableEngineParams boardSecurityTableParams = new BoardSecurityTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD, TestUtils.SECURITY);

    @Test
    void getList() throws IOException
    {
        log.debug(service.getList(listParams).toString());
    }

    @Test
    void getInfo() throws IOException
    {
        log.debug(service.getInfo(infoParams).toString());
    }

    @Test
    void getEngineInfo() throws IOException
    {
        log.debug(service.getEngineInfo(infoParams).toString());
    }

    @Test
    void getTimeTableInfo() throws IOException
    {
        log.debug(service.getTimeTableInfo(infoParams).toString());
    }

    @Test
    void getDailyTableInfo() throws IOException
    {
        log.debug(service.getDailyTableInfo(infoParams).toString());
    }

    @Test
    void getMarkets() throws IOException
    {
        log.debug(service.getMarkets(marketsParams).toString());
    }

    @Test
    void getMarketInfo() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketInfo(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketBoards() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketBoards(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketBoardGroups() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketBoardGroups(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketSecurityFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketSecurityFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketDataFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketDataFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketTradeFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketTradeFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketOrderBookFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketOrderBookFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketHistoryFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketHistoryFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketTradeHistoryFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketTradeHistoryFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketDataYieldFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketDataYieldFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketTradeYieldFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketTradeYieldFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketHistoryYieldFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketHistoryYieldFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketSecurityStatisticFields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketSecurityStatisticFields(new MarketInfoEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getMarketSecuritiesTable() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketSecuritiesTable(new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
        log.debug(service.getMarketSecuritiesTable(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketSecurities() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketSecurities(new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
        log.debug(service.getMarketSecurities(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketData() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketData(new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
        log.debug(service.getMarketData(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketDataVersions() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketDataVersions(new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
        log.debug(service.getMarketDataVersions(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketDataYields() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getMarketDataYields(new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
        log.debug(service.getMarketDataYields(marketSecurityTableParams).toString());
    }

    @Test
    void getBoards() throws IOException
    {
        Iterable<MarketResponse> markets = service.getMarkets(marketsParams);
        for (MarketResponse market : markets)
            log.debug(service.getBoards(new BoardsEngineParams(TestUtils.ENGINE, market.getStringFields().get(MarketResponse.Fields.NAME))).toString());
    }

    @Test
    void getBoard() throws IOException
    {
        log.debug(service.getBoard(boardParams).toString());
    }

    @Test
    void getBoardSecuritiesTable() throws IOException
    {
        log.debug(service.getBoardSecuritiesTable(boardSecuritiesTableParams).toString());
        log.debug(service.getBoardSecurityTable(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardSecurities() throws IOException
    {
        log.debug(service.getBoardSecurities(boardSecuritiesTableParams).toString());
        log.debug(service.getBoardSecurity(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardMarketData() throws IOException
    {
        log.debug(service.getBoardMarketData(boardSecuritiesTableParams).toString());
        log.debug(service.getBoardMarketData(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardDataVersions() throws IOException
    {
        log.debug(service.getBoardDataVersions(boardSecuritiesTableParams).toString());
        log.debug(service.getBoardDataVersions(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardMarketDataYields() throws IOException
    {
        log.debug(service.getBoardMarketDataYields(boardSecuritiesTableParams).toString());
        log.debug(service.getBoardMarketDataYields(boardSecurityTableParams).toString());
    }
}
