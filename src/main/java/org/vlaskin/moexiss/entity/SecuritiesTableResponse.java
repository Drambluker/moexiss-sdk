package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SecuritiesTableResponse
{
    private final List<SecurityResponse> securities;
    private final List<MarketDataResponse> marketData;
    private final List<DataVersionResponse> dataVersions;
    private final List<MarketDataYieldResponse> marketDataYields;
}
