package org.vlaskin.moexiss.live;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDataResponse;
import org.vlaskin.moexiss.entity.MarketDataResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.service.engine.params.ListEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecurityTableEngineParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoexIssLiveContractIT
{
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
    void deserializesIndexAnalyticsContract() throws IOException
    {
        List<IndexAnalyticsDataResponse> analytics = retryOnIoFailure(() ->
                client.getStatistics().getIndexAnalyticsData(new AnalyticsStatisticParams("IMOEX")));

        assertFalse(analytics.isEmpty());
        IndexAnalyticsDataResponse first = analytics.getFirst();
        assertEquals("IMOEX", first.getStringFields().get(IndexAnalyticsDataResponse.Fields.INDEX_CODE));
        assertNotNull(first.getStringFields().get(IndexAnalyticsDataResponse.Fields.SECURITY_CODE));
        assertNotNull(first.getDoubleFields().get(IndexAnalyticsDataResponse.Fields.WEIGHT));
        assertNotNull(first.getLocalDateFields().get(IndexAnalyticsDataResponse.Fields.TRADE_DATE));
    }

    private static <T> T retryOnIoFailure(IoSupplier<T> request) throws IOException
    {
        IOException lastFailure = null;
        for (int attempt = 1; attempt <= 3; attempt++)
        {
            try
            {
                return request.get();
            }
            catch (IOException exception)
            {
                lastFailure = exception;
            }
        }
        throw lastFailure;
    }

    @FunctionalInterface
    private interface IoSupplier<T>
    {
        T get() throws IOException;
    }
}
