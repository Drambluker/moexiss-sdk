package org.vlaskin.moexiss.live;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.CursorResponse;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDataResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.entity.MarketDataResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.service.engine.params.BoardEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardsEngineParams;
import org.vlaskin.moexiss.service.engine.params.InfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.ListEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketsEngineParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.IndicesStatisticParams;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoexIssLiveContractIT
{
    private static final int MAX_ATTEMPTS = 3;
    private static final int RETRY_DELAY_SECONDS = 2;

    private final MoexClient client = new MoexClient();

    @Test
    void deserializesTradingSystemsContract() throws IOException
    {
        List<EngineResponse> engines = retryOnIoFailure(
                () -> client.getEngines().getList(new ListEngineParams()));

        assertFalse(engines.isEmpty());
        assertTrue(engines.stream().anyMatch(engine ->
                "stock".equals(engine.getStringFields().get(EngineResponse.Fields.NAME))));
    }

    @Test
    void deserializesConfiguredMarketStructureContract() throws IOException
    {
        EngineResponse engine = retryOnIoFailure(() ->
                client.getEngines().getEngineInfo(new InfoEngineParams("stock")))
                .orElseThrow(() -> new AssertionError("Торговая система stock отсутствует"));
        assertEquals("stock", engine.getStringFields().get(EngineResponse.Fields.NAME));
        assertNotNull(engine.getStringFields().get(EngineResponse.Fields.TITLE));

        MarketResponse market = retryOnIoFailure(() ->
                client.getEngines().getMarkets(new MarketsEngineParams("stock"))).stream()
                .filter(candidate -> "shares".equals(
                        candidate.getStringFields().get(MarketResponse.Fields.NAME)))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Рынок shares отсутствует"));
        assertNotNull(market.getStringFields().get(MarketResponse.Fields.TITLE));

        List<BoardResponse> boards = retryOnIoFailure(() ->
                client.getEngines().getBoards(new BoardsEngineParams("stock", "shares")));
        assertTrue(boards.stream().anyMatch(board ->
                "TQBR".equals(board.getStringFields().get(BoardResponse.Fields.CODE))));

        BoardResponse board = retryOnIoFailure(() ->
                client.getEngines().getBoard(new BoardEngineParams("stock", "shares", "TQBR")))
                .orElseThrow(() -> new AssertionError("Режим торгов TQBR отсутствует"));
        assertEquals("TQBR", board.getStringFields().get(BoardResponse.Fields.CODE));
        assertNotNull(board.getStringFields().get(BoardResponse.Fields.TITLE));
    }

    @Test
    void deserializesSecuritySearchContract() throws IOException
    {
        ListSecurityParams params = new ListSecurityParams();
        params.setQuery("SBER");
        params.setLimit(20);

        List<SecurityResponse> securities = retryOnIoFailure(
                () -> client.getSecurities().getList(params));

        assertTrue(securities.stream().anyMatch(security ->
                "SBER".equals(security.getStringFields().get(SecurityResponse.Fields.CODE))));
    }

    @Test
    void deserializesMarketDataContract() throws IOException
    {
        MarketSecurityTableEngineParams params =
                new MarketSecurityTableEngineParams("stock", "shares", "SBER");

        List<MarketDataResponse> marketData = retryOnIoFailure(
                () -> client.getEngines().getMarketData(params));

        assertFalse(marketData.isEmpty());
        assertEquals("SBER", marketData.getFirst().getStringFields()
                .get(MarketDataResponse.Fields.SECURITY_CODE));
        assertNotNull(marketData.getFirst().getLocalDateTimeFields()
                .get(MarketDataResponse.Fields.SYS_TIME));
    }

    @Test
    void deserializesSecuritiesUsedBySynchronizationContract() throws IOException
    {
        MarketSecuritiesTableEngineParams marketParams =
                new MarketSecuritiesTableEngineParams("stock", "shares");
        marketParams.setPrimaryBoard("1");
        marketParams.setSecurities(List.of("SBER"));

        SecurityResponse marketSecurity = retryOnIoFailure(() ->
                client.getEngines().getMarketSecurities(marketParams)).stream()
                .filter(security -> "SBER".equals(
                        security.getStringFields().get(SecurityResponse.Fields.CODE)))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("SBER не найден на основном режиме торгов"));
        assertSecurityFields(marketSecurity);

        BoardSecuritiesTableEngineParams boardParams =
                new BoardSecuritiesTableEngineParams("stock", "shares", "TQBR");
        boardParams.setSecurities(List.of("SBER"));

        SecurityResponse boardSecurity = retryOnIoFailure(() ->
                client.getEngines().getBoardSecurities(boardParams)).stream()
                .filter(security -> "SBER".equals(
                        security.getStringFields().get(SecurityResponse.Fields.CODE)))
                .findFirst()
                .orElseThrow(() -> new AssertionError("SBER не найден на режиме торгов TQBR"));
        assertSecurityFields(boardSecurity);
        assertEquals("TQBR", boardSecurity.getStringFields().get(SecurityResponse.Fields.BOARD_CODE));
    }

    @Test
    void deserializesIndicesContract() throws IOException
    {
        IndexResponse index = retryOnIoFailure(() ->
                client.getStatistics().getIndices(new IndicesStatisticParams())).stream()
                .filter(candidate -> "IMOEX".equals(
                        candidate.getStringFields().get(IndexResponse.Fields.CODE)))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Индекс IMOEX отсутствует"));

        assertNotNull(index.getStringFields().get(IndexResponse.Fields.SHORTNAME));
        assertNotNull(index.getLocalDateFields().get(IndexResponse.Fields.FROM));
    }

    @Test
    void deserializesIndexAnalyticsContract() throws IOException
    {
        AnalyticsStatisticParams firstPageParams = new AnalyticsStatisticParams("IMOEX");
        IndexAnalyticsResponse firstPage = retryOnIoFailure(() ->
                client.getStatistics().getIndexAnalytics(firstPageParams));
        CursorResponse cursor = firstPage.getCursors().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Курсор аналитики IMOEX отсутствует"));
        long currentIndex = requiredCursorField(cursor, CursorResponse.Fields.INDEX);
        long pageSize = requiredCursorField(cursor, CursorResponse.Fields.PAGE_SIZE);
        long total = requiredCursorField(cursor, CursorResponse.Fields.TOTAL);
        assertTrue(pageSize > 0);
        assertTrue(total > pageSize);

        AnalyticsStatisticParams secondPageParams = new AnalyticsStatisticParams("IMOEX");
        secondPageParams.setStartIndex(Math.toIntExact(currentIndex + pageSize));
        List<IndexAnalyticsDataResponse> secondPage = retryOnIoFailure(() ->
                client.getStatistics().getIndexAnalyticsData(secondPageParams));

        assertFalse(firstPage.getData().isEmpty());
        assertFalse(secondPage.isEmpty());
        firstPage.getData().forEach(MoexIssLiveContractIT::assertAnalyticsFields);
        secondPage.forEach(MoexIssLiveContractIT::assertAnalyticsFields);

        Set<String> firstPageCodes = firstPage.getData().stream()
                .map(analytics -> analytics.getStringFields()
                        .get(IndexAnalyticsDataResponse.Fields.SECURITY_CODE))
                .collect(Collectors.toSet());
        assertTrue(secondPage.stream()
                .map(analytics -> analytics.getStringFields()
                        .get(IndexAnalyticsDataResponse.Fields.SECURITY_CODE))
                .noneMatch(firstPageCodes::contains));
    }

    private static long requiredCursorField(CursorResponse cursor, CursorResponse.Fields field)
    {
        Long value = cursor.getLongFields().get(field);
        assertNotNull(value);
        return value;
    }

    private static void assertSecurityFields(SecurityResponse security)
    {
        assertEquals("SBER", security.getStringFields().get(SecurityResponse.Fields.CODE));
        assertNotNull(security.getStringFields().get(SecurityResponse.Fields.NAME));
        assertNotNull(security.getStringFields().get(SecurityResponse.Fields.BOARD_CODE));
        assertTrue(security.getIntegerFields().get(SecurityResponse.Fields.LOT_SIZE) > 0);
        assertNotNull(security.getStringFields().get(SecurityResponse.Fields.STATUS));
        assertTrue(security.getDoubleFields().containsKey(SecurityResponse.Fields.PREV_PRICE)
                || security.getDoubleFields().containsKey(SecurityResponse.Fields.PREV_WA_PRICE)
                || security.getDoubleFields().containsKey(SecurityResponse.Fields.PREV_LEGAL_CLOSE_PRICE));
    }

    private static void assertAnalyticsFields(IndexAnalyticsDataResponse analytics)
    {
        assertEquals("IMOEX", analytics.getStringFields()
                .get(IndexAnalyticsDataResponse.Fields.INDEX_CODE));
        assertNotNull(analytics.getStringFields()
                .get(IndexAnalyticsDataResponse.Fields.SECURITY_CODE));
        assertNotNull(analytics.getDoubleFields().get(IndexAnalyticsDataResponse.Fields.WEIGHT));
        assertNotNull(analytics.getLocalDateFields().get(IndexAnalyticsDataResponse.Fields.TRADE_DATE));
    }

    private static <T> T retryOnIoFailure(IoSupplier<T> request) throws IOException
    {
        IOException lastFailure = null;
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++)
        {
            try
            {
                return request.get();
            }
            catch (IOException exception)
            {
                lastFailure = exception;
                if (attempt < MAX_ATTEMPTS)
                    waitBeforeRetry();
            }
        }
        throw lastFailure;
    }

    private static void waitBeforeRetry() throws IOException
    {
        try
        {
            TimeUnit.SECONDS.sleep(RETRY_DELAY_SECONDS);
        }
        catch (InterruptedException exception)
        {
            Thread.currentThread().interrupt();
            throw new IOException("Ожидание повторного запроса прервано", exception);
        }
    }

    @FunctionalInterface
    private interface IoSupplier<T>
    {
        T get() throws IOException;
    }
}
