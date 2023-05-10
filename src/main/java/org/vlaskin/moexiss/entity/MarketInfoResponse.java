package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class MarketInfoResponse
{
    private List<BoardResponse> boards;
    private List<BoardGroupResponse> boardGroups;
    private List<TableFieldResponse> securityFields;
    private List<TableFieldResponse> marketDataFields;
    private List<TableFieldResponse> tradeFields;
    private List<TableFieldResponse> orderBookFields;
    private List<TableFieldResponse> historyFields;
    private List<TableFieldResponse> tradeHistoryFields;
    private List<TableFieldResponse> marketDataYieldFields;
    private List<TableFieldResponse> tradeYieldFields;
    private List<TableFieldResponse> historyYieldFields;
    private List<TableFieldResponse> securityStatisticFields;
}
