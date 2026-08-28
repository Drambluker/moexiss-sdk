package org.vlaskin.moexiss.service.statistic;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.CursorResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDatesResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDataResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.TickerInfoResponse;
import org.vlaskin.moexiss.entity.TickerResponse;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.IndicesStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickerInfoStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickersStatisticParams;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatisticServiceTest
{
    @Test
    void deserializesCompleteAnalyticsResponseAndDateRange() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        StatisticService service = new StatisticService("https://fixture.test", transport);
        AnalyticsStatisticParams params = new AnalyticsStatisticParams("IMOEX");

        IndexAnalyticsResponse response = service.getIndexAnalytics(params);
        List<IndexAnalyticsDatesResponse> dates = service.getIndexAnalyticsDates(params);

        assertEquals(1, response.getData().size());
        assertEquals(1, response.getCursors().size());
        assertEquals(1, response.getDates().size());
        assertEquals(LocalDate.of(2001, 1, 3), dates.getFirst()
                .get(IndexAnalyticsDatesResponse.Fields.FROM, LocalDate.class));
        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/IMOEX.json"
                        + "?iss.meta=on&iss.only=analytics.dates&lang=ru&start=0&limit=20&tradingsession=3",
                transport.getRequestedUrl());
    }

    @Test
    void deserializesAvailableIndicesAndTickers() throws IOException
    {
        FixtureTransport indicesTransport = new FixtureTransport("analytics-indices.json");
        StatisticService indicesService = new StatisticService("https://fixture.test", indicesTransport);
        IndicesStatisticParams indicesParams = new IndicesStatisticParams();
        indicesParams.setSecurityCollection("stock shares");

        List<IndexResponse> indices = indicesService.getIndices(indicesParams);

        assertEquals(1, indices.size());
        assertEquals("IMOEX", indices.getFirst().getCode());
        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics.json"
                        + "?iss.meta=on&lang=ru&tradingsession=3&security_collection=stock+shares",
                indicesTransport.getRequestedUrl());

        FixtureTransport tickersTransport = new FixtureTransport("tickers.json");
        StatisticService tickersService = new StatisticService("https://fixture.test", tickersTransport);
        TickersStatisticParams tickersParams = new TickersStatisticParams("IMOEX");
        tickersParams.setDate(LocalDate.of(2026, 9, 9));

        List<TickerResponse> tickers = tickersService.getTickers(tickersParams);

        assertEquals("AFKS", tickers.getFirst().get(TickerResponse.Fields.TICKER, String.class));
        assertEquals(TradingSession.TOTAL, tickers.getFirst().getTradingSession());
        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/IMOEX"
                        + "/tickers.json?iss.meta=on&tradingsession=3&date=2026-09-09",
                tickersTransport.getRequestedUrl());
    }

    @Test
    void deserializesTickerHistoryAndCursor() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("ticker-info.json");
        StatisticService service = new StatisticService("https://fixture.test", transport);
        TickerInfoStatisticParams params = new TickerInfoStatisticParams(
                "IMOEX", "SBER", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 31));
        params.setStartIndex(20);

        TickerInfoResponse response = service.getTickerInfo(params);

        assertEquals(1, response.getTickers().size());
        assertEquals(1, response.getCursors().size());
        assertEquals("SBER", service.getTickerInfoData(params).getFirst()
                .get(TickerResponse.Fields.TICKER, String.class));
        assertEquals(21L, service.getTickerInfoCursor(params).getFirst().getTotal());
        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/IMOEX"
                        + "/tickers/SBER.json?iss.meta=on&iss.only=ticker.cursor&lang=ru"
                        + "&tradingsession=3&start=20&from=2026-08-01&till=2026-08-31",
                transport.getRequestedUrl());
    }

    @Test
    void deserializesTypedAnalyticsFields() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);

        List<IndexAnalyticsDataResponse> analytics = client.getStatistics()
                .getIndexAnalyticsData(new AnalyticsStatisticParams("IMOEX"));

        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/IMOEX.json"
                + "?iss.meta=on&iss.only=analytics&lang=ru&start=0&limit=20&tradingsession=3",
                transport.getRequestedUrl());
        assertEquals(1, analytics.size());
        assertEquals(LocalDate.of(2026, 8, 27), analytics.getFirst().getTradeDate());
        assertEquals(LocalDate.of(2026, 8, 28), analytics.getFirst().getTradeSessionDate());
        assertEquals(12.5, analytics.getFirst().getWeight());
        assertEquals(TradingSession.TOTAL, analytics.getFirst().getTradingSession());
    }

    @Test
    void usesExplicitAnalyticsStartIndex() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);
        AnalyticsStatisticParams params = new AnalyticsStatisticParams("IMOEX");
        params.setPageIndex(7);
        params.setLimit(100);
        params.setStartIndex(20);

        client.getStatistics().getIndexAnalyticsData(params);

        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/IMOEX.json"
                + "?iss.meta=on&iss.only=analytics&lang=ru&start=20&limit=100&tradingsession=3",
                transport.getRequestedUrl());
    }

    @Test
    void deserializesAnalyticsCursor() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);

        List<CursorResponse> cursors = client.getStatistics()
                .getIndexAnalyticsCursor(new AnalyticsStatisticParams("IMOEX"));

        assertEquals(1, cursors.size());
        CursorResponse cursor = cursors.getFirst();
        assertEquals(0L, cursor.getIndex());
        assertEquals(46L, cursor.getTotal());
        assertEquals(20L, cursor.getPageSize());
    }

    @Test
    void encodesIndexPathSegment() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);

        client.getStatistics().getIndexAnalyticsData(
                new AnalyticsStatisticParams("Индекс /+&#"));

        assertEquals("https://fixture.test/iss/statistics/engines/stock/markets/index/analytics/"
                + "%D0%98%D0%BD%D0%B4%D0%B5%D0%BA%D1%81%20%2F%2B%26%23.json"
                + "?iss.meta=on&iss.only=analytics&lang=ru&start=0&limit=20&tradingsession=3",
                transport.getRequestedUrl());
    }

    @Test
    void rejectsReversedDateRangeBeforeRequest()
    {
        FixtureTransport transport = new FixtureTransport("analytics.json");
        StatisticService service = new StatisticService("https://fixture.test", transport);
        TickerInfoStatisticParams params = new TickerInfoStatisticParams(
                "IMOEX", "SBER", LocalDate.of(2026, 9, 2), LocalDate.of(2026, 9, 1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getTickerInfoData(params));

        assertEquals("Parameter from must not be later than till",
                exception.getMessage());
        assertNull(transport.getRequestedUrl());
    }
}
