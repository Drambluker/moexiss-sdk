package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SecurityTable
{
    private final Security security;
    private final List<MarketData> marketData;
    private final List<DataVersion> dataVersions;
    private final List<MarketDataYield> marketDataYields;
}
