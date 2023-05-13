package org.vlaskin.moexiss.service.engine;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
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
    private static final MarketInfoEngineParams marketInfoParams = new MarketInfoEngineParams(TestUtils.ENGINE, TestUtils.MARKET);
    private static final MarketSecuritiesTableEngineParams marketSecuritiesTableParams = new MarketSecuritiesTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET);
    private static final MarketSecurityTableEngineParams marketSecurityTableParams = new MarketSecurityTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.SECURITY);
    private static final BoardsEngineParams boardsParams = new BoardsEngineParams(TestUtils.ENGINE, TestUtils.MARKET);
    private static final BoardEngineParams boardParams = new BoardEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD);
    private static final BoardSecuritiesTableEngineParams boardSecuritiesTableParams = new BoardSecuritiesTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD);
    private static final BoardSecurityTableEngineParams boardSecurityTableParams = new BoardSecurityTableEngineParams(TestUtils.ENGINE, TestUtils.MARKET, TestUtils.BOARD, TestUtils.SECURITY);

    @Test
    void getList() throws IOException
    {
        log.info(service.getList(listParams).toString());
    }

    @Test
    void getInfo() throws IOException
    {
        log.info(service.getInfo(infoParams).toString());
    }

    @Test
    void getEngineInfo() throws IOException
    {
        log.info(service.getEngineInfo(infoParams).toString());
    }

    @Test
    void getTimeTableInfo() throws IOException
    {
        log.info(service.getTimeTableInfo(infoParams).toString());
    }

    @Test
    void getDailyTableInfo() throws IOException
    {
        log.info(service.getDailyTableInfo(infoParams).toString());
    }

    @Test
    void getMarkets() throws IOException
    {
        log.info(service.getMarkets(marketsParams).toString());
    }

    @Test
    void getMarketInfo() throws IOException
    {
        log.info(service.getMarketInfo(marketInfoParams).toString());
    }

    @Test
    void getMarketBoards() throws IOException
    {
        log.info(service.getMarketBoards(marketInfoParams).toString());
    }

    @Test
    void getMarketBoardGroups() throws IOException
    {
        log.info(service.getMarketBoardGroups(marketInfoParams).toString());
    }

    @Test
    void getMarketSecurityFields() throws IOException
    {
        log.info(service.getMarketSecurityFields(marketInfoParams).toString());
    }

    @Test
    void getMarketDataFields() throws IOException
    {
        log.info(service.getMarketDataFields(marketInfoParams).toString());
    }

    @Test
    void getMarketTradeFields() throws IOException
    {
        log.info(service.getMarketTradeFields(marketInfoParams).toString());
    }

    @Test
    void getMarketOrderBookFields() throws IOException
    {
        log.info(service.getMarketOrderBookFields(marketInfoParams).toString());
    }

    @Test
    void getMarketHistoryFields() throws IOException
    {
        log.info(service.getMarketHistoryFields(marketInfoParams).toString());
    }

    @Test
    void getMarketTradeHistoryFields() throws IOException
    {
        log.info(service.getMarketTradeHistoryFields(marketInfoParams).toString());
    }

    @Test
    void getMarketDataYieldFields() throws IOException
    {
        log.info(service.getMarketDataYieldFields(marketInfoParams).toString());
    }

    @Test
    void getMarketTradeYieldFields() throws IOException
    {
        log.info(service.getMarketTradeYieldFields(marketInfoParams).toString());
    }

    @Test
    void getMarketHistoryYieldFields() throws IOException
    {
        log.info(service.getMarketHistoryYieldFields(marketInfoParams).toString());
    }

    @Test
    void getMarketSecurityStatisticFields() throws IOException
    {
        log.info(service.getMarketSecurityStatisticFields(marketInfoParams).toString());
    }

    @Test
    void getMarketSecuritiesTable() throws IOException
    {
        log.info(service.getMarketSecuritiesTable(marketSecuritiesTableParams).toString());
        log.info(service.getMarketSecuritiesTable(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketSecurities() throws IOException
    {
        log.info(service.getMarketSecurities(marketSecuritiesTableParams).toString());
        log.info(service.getMarketSecurities(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketData() throws IOException
    {
        log.info(service.getMarketData(marketSecuritiesTableParams).toString());
        log.info(service.getMarketData(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketDataVersions() throws IOException
    {
        log.info(service.getMarketDataVersions(marketSecuritiesTableParams).toString());
        log.info(service.getMarketDataVersions(marketSecurityTableParams).toString());
    }

    @Test
    void getMarketDataYields() throws IOException
    {
        log.info(service.getMarketDataYields(marketSecuritiesTableParams).toString());
        log.info(service.getMarketDataYields(marketSecurityTableParams).toString());
    }

    @Test
    void getBoards() throws IOException
    {
        log.info(service.getBoards(boardsParams).toString());
    }

    @Test
    void getBoard() throws IOException
    {
        log.info(service.getBoard(boardParams).toString());
    }

    @Test
    void getBoardSecuritiesTable() throws IOException
    {
        log.info(service.getBoardSecuritiesTable(boardSecuritiesTableParams).toString());
        log.info(service.getBoardSecurityTable(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardSecurities() throws IOException
    {
        log.info(service.getBoardSecurities(boardSecuritiesTableParams).toString());
        log.info(service.getBoardSecurity(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardMarketData() throws IOException
    {
        log.info(service.getBoardMarketData(boardSecuritiesTableParams).toString());
        log.info(service.getBoardMarketData(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardDataVersions() throws IOException
    {
        log.info(service.getBoardDataVersions(boardSecuritiesTableParams).toString());
        log.info(service.getBoardDataVersions(boardSecurityTableParams).toString());
    }

    @Test
    void getBoardMarketDataYields() throws IOException
    {
        log.info(service.getBoardMarketDataYields(boardSecuritiesTableParams).toString());
        log.info(service.getBoardMarketDataYields(boardSecurityTableParams).toString());
    }
}
