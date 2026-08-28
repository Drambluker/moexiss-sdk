package org.vlaskin.moexiss.live;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.CursorResponse;
import org.vlaskin.moexiss.entity.DictionaryResponse;
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
import java.time.LocalDateTime;
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
        assertTrue(engines.stream().anyMatch(engine -> "stock".equals(engine.getName())));
    }

    @Test
    void deserializesDictionariesContract() throws IOException
    {
        DictionaryResponse dictionaries = retryOnIoFailure(() -> client.getDictionaries().getAll());

        assertFalse(dictionaries.getEngines().isEmpty());
        assertFalse(dictionaries.getMarkets().isEmpty());
        assertFalse(dictionaries.getBoards().isEmpty());
        assertFalse(dictionaries.getBoardGroups().isEmpty());
        assertFalse(dictionaries.getDurations().isEmpty());
        assertFalse(dictionaries.getSecurityTypes().isEmpty());
        assertFalse(dictionaries.getSecurityGroups().isEmpty());
        assertFalse(dictionaries.getSecurityCollections().isEmpty());
    }

    @Test
    void deserializesConfiguredMarketStructureContract() throws IOException
    {
        EngineResponse engine = retryOnIoFailure(() ->
                client.getEngines().getEngineInfo(new InfoEngineParams("stock")))
                .orElseThrow(() -> new AssertionError("Trading engine stock is missing"));
        assertEquals("stock", engine.getName());
        assertNotNull(engine.getTitle());

        MarketResponse market = retryOnIoFailure(() ->
                client.getEngines().getMarkets(new MarketsEngineParams("stock"))).stream()
                .filter(candidate -> "shares".equals(candidate.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Market shares is missing"));
        assertNotNull(market.getTitle());

        List<BoardResponse> boards = retryOnIoFailure(() ->
                client.getEngines().getBoards(new BoardsEngineParams("stock", "shares")));
        assertTrue(boards.stream().anyMatch(board -> "TQBR".equals(board.getCode())));

        BoardResponse board = retryOnIoFailure(() ->
                client.getEngines().getBoard(new BoardEngineParams("stock", "shares", "TQBR")))
                .orElseThrow(() -> new AssertionError("Board TQBR is missing"));
        assertEquals("TQBR", board.getCode());
        assertNotNull(board.getTitle());
    }

    @Test
    void deserializesSecuritySearchContract() throws IOException
    {
        ListSecurityParams params = new ListSecurityParams();
        params.setQuery("SBER");
        params.setLimit(20);

        List<SecurityResponse> securities = retryOnIoFailure(
                () -> client.getSecurities().getList(params));

        assertTrue(securities.stream().anyMatch(security -> "SBER".equals(security.getCode())));
    }

    @Test
    void deserializesMarketDataContract() throws IOException
    {
        MarketSecurityTableEngineParams params =
                new MarketSecurityTableEngineParams("stock", "shares", "SBER");

        List<MarketDataResponse> marketData = retryOnIoFailure(
                () -> client.getEngines().getMarketData(params));

        assertFalse(marketData.isEmpty());
        assertEquals("SBER", marketData.getFirst()
                .get(MarketDataResponse.Fields.SECURITY_CODE, String.class));
        assertNotNull(marketData.getFirst()
                .get(MarketDataResponse.Fields.SYS_TIME, LocalDateTime.class));
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
                .filter(security -> "SBER".equals(security.getCode()))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("SBER is missing from its primary board"));
        assertSecurityFields(marketSecurity);

        BoardSecuritiesTableEngineParams boardParams =
                new BoardSecuritiesTableEngineParams("stock", "shares", "TQBR");
        boardParams.setSecurities(List.of("SBER"));

        SecurityResponse boardSecurity = retryOnIoFailure(() ->
                client.getEngines().getBoardSecurities(boardParams)).stream()
                .filter(security -> "SBER".equals(security.getCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("SBER is missing from board TQBR"));
        assertSecurityFields(boardSecurity);
        assertEquals("TQBR", boardSecurity.getBoardCode());
    }

    @Test
    void deserializesIndicesContract() throws IOException
    {
        IndexResponse index = retryOnIoFailure(() ->
                client.getStatistics().getIndices(new IndicesStatisticParams())).stream()
                .filter(candidate -> "IMOEX".equals(candidate.getCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Index IMOEX is missing"));

        assertNotNull(index.getShortName());
        assertNotNull(index.getFrom());
    }

    @Test
    void deserializesIndexAnalyticsContract() throws IOException
    {
        AnalyticsStatisticParams firstPageParams = new AnalyticsStatisticParams("IMOEX");
        IndexAnalyticsResponse firstPage = retryOnIoFailure(() ->
                client.getStatistics().getIndexAnalytics(firstPageParams));
        CursorResponse cursor = firstPage.getCursors().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("IMOEX analytics cursor is missing"));
        assertNotNull(cursor.getIndex());
        assertNotNull(cursor.getPageSize());
        assertNotNull(cursor.getTotal());
        long currentIndex = cursor.getIndex();
        long pageSize = cursor.getPageSize();
        long total = cursor.getTotal();
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
                .map(IndexAnalyticsDataResponse::getSecurityCode)
                .collect(Collectors.toSet());
        assertTrue(secondPage.stream()
                .map(IndexAnalyticsDataResponse::getSecurityCode)
                .noneMatch(firstPageCodes::contains));
    }

    private static void assertSecurityFields(SecurityResponse security)
    {
        assertEquals("SBER", security.getCode());
        assertNotNull(security.getName());
        assertNotNull(security.getBoardCode());
        assertTrue(security.getLotSize() > 0);
        assertNotNull(security.getStatus());
        assertTrue(security.getPrevPrice() != null
                || security.getPrevWaPrice() != null
                || security.getPrevLegalClosePrice() != null);
    }

    private static void assertAnalyticsFields(IndexAnalyticsDataResponse analytics)
    {
        assertEquals("IMOEX", analytics.getIndexCode());
        assertNotNull(analytics.getSecurityCode());
        assertNotNull(analytics.getWeight());
        assertNotNull(analytics.getTradeDate());
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
            throw new IOException("Retry wait was interrupted", exception);
        }
    }

    @FunctionalInterface
    private interface IoSupplier<T>
    {
        T get() throws IOException;
    }
}
