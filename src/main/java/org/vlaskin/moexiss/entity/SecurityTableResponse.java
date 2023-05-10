package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SecurityTableResponse
{
    private final SecurityResponse security;
    private final List<MarketDataResponse> marketData;
    private final List<DataVersionResponse> dataVersions;
    private final List<MarketDataYieldResponse> marketDataYields;
}
