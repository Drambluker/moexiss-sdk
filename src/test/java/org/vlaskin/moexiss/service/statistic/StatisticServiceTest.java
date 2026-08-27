package org.vlaskin.moexiss.service.statistic;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.IndexAnalyticsDataResponse;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticServiceTest
{
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
        assertEquals(LocalDate.of(2026, 8, 27), analytics.getFirst().getLocalDateFields()
                .get(IndexAnalyticsDataResponse.Fields.TRADE_DATE));
        assertEquals(12.5, analytics.getFirst().getDoubleFields()
                .get(IndexAnalyticsDataResponse.Fields.WEIGHT));
        assertEquals(TradingSession.TOTAL, analytics.getFirst().getTradingSession());
    }
}
