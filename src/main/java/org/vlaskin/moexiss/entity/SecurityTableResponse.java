package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/** Составной ответ по одному инструменту и связанным рыночным данным. */
@Getter
@ToString
public final class SecurityTableResponse
{
    private final SecurityResponse security;
    private final List<MarketDataResponse> marketData;
    private final List<DataVersionResponse> dataVersions;
    private final List<MarketDataYieldResponse> marketDataYields;

    /**
     * @param security статические данные инструмента; могут отсутствовать в ответе
     * @param marketData текущие рыночные данные
     * @param dataVersions версии данных
     * @param marketDataYields доходности
     */
    @Builder
    public SecurityTableResponse(SecurityResponse security,
                                 List<MarketDataResponse> marketData,
                                 List<DataVersionResponse> dataVersions,
                                 List<MarketDataYieldResponse> marketDataYields)
    {
        this.security = security;
        this.marketData = ResponseLists.immutableCopy(marketData);
        this.dataVersions = ResponseLists.immutableCopy(dataVersions);
        this.marketDataYields = ResponseLists.immutableCopy(marketDataYields);
    }
}
