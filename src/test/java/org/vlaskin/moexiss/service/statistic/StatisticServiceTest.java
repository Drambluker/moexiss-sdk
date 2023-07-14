package org.vlaskin.moexiss.service.statistic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.service.TestUtils;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.IndicesStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickerInfoStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickersStatisticParams;

import java.io.IOException;

// TODO
@Slf4j
class StatisticServiceTest
{
    private static final MoexClient client = new MoexClient();
    private static final StatisticService service = client.getStatistics();

    private static final IndicesStatisticParams indicesParams = new IndicesStatisticParams();
    private static final AnalyticsStatisticParams analyticsParams = new AnalyticsStatisticParams(TestUtils.INDEX);
    private static final TickersStatisticParams tickersParams = new TickersStatisticParams(TestUtils.INDEX);
    private static final TickerInfoStatisticParams tickerInfoParams = new TickerInfoStatisticParams(TestUtils.INDEX, TestUtils.TICKER);

    @Test
    void getIndices() throws IOException
    {
        log.debug(service.getIndices(indicesParams).toString());
    }

    @Test
    void getIndexAnalytics() throws IOException
    {
        log.debug(service.getIndexAnalytics(analyticsParams).toString());
    }

    @Test
    void getIndexAnalyticsData() throws IOException
    {
        log.debug(service.getIndexAnalyticsData(analyticsParams).toString());
    }

    @Test
    void getIndexAnalyticsCursor() throws IOException
    {
        log.debug(service.getIndexAnalyticsCursor(analyticsParams).toString());
    }

    @Test
    void getIndexAnalyticsDates() throws IOException
    {
        log.debug(service.getIndexAnalyticsDates(analyticsParams).toString());
    }

    @Test
    void getTickers() throws IOException
    {
        log.debug(service.getTickers(tickersParams).toString());
    }

    @Test
    void getTickerInfo() throws IOException
    {
        log.debug(service.getTickerInfo(tickerInfoParams).toString());
    }

    @Test
    void getTickerInfoData() throws IOException
    {
        log.debug(service.getTickerInfoData(tickerInfoParams).toString());
    }

    @Test
    void getTickerInfoCursor() throws IOException
    {
        log.debug(service.getTickerInfoCursor(tickerInfoParams).toString());
    }
}
