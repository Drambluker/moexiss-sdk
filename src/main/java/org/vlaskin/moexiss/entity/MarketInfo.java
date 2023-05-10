package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class MarketInfo
{
    private List<Board> boards;
    private List<BoardGroup> boardGroups;
    private List<TableField> securityFields;
    private List<TableField> marketDataFields;
    private List<TableField> tradeFields;
    private List<TableField> orderBookFields;
    private List<TableField> historyFields;
    private List<TableField> tradeHistoryFields;
    private List<TableField> marketDataYieldFields;
    private List<TableField> tradeYieldFields;
    private List<TableField> historyYieldFields;
    private List<TableField> securityStatisticFields;
}
