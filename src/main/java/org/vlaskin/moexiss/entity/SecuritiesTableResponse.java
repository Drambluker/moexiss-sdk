package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/** Составной ответ таблицы инструментов, рыночных данных, версий и доходностей. */
@Getter
@ToString
public final class SecuritiesTableResponse
{
    private final List<SecurityResponse> securities;
    private final List<MarketDataResponse> marketData;
    private final List<DataVersionResponse> dataVersions;
    private final List<MarketDataYieldResponse> marketDataYields;

    /**
     * @param securities статические данные инструментов
     * @param marketData текущие рыночные данные
     * @param dataVersions версии данных
     * @param marketDataYields доходности
     */
    @Builder
    public SecuritiesTableResponse(List<SecurityResponse> securities,
                                   List<MarketDataResponse> marketData,
                                   List<DataVersionResponse> dataVersions,
                                   List<MarketDataYieldResponse> marketDataYields)
    {
        this.securities = ResponseLists.immutableCopy(securities);
        this.marketData = ResponseLists.immutableCopy(marketData);
        this.dataVersions = ResponseLists.immutableCopy(dataVersions);
        this.marketDataYields = ResponseLists.immutableCopy(marketDataYields);
    }
}
