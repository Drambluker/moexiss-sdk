package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Составной ответ с режимами торгов рынка и описанием полей его таблиц.
 */

@Getter
@ToString
public final class MarketInfoResponse
{
    private final List<BoardResponse> boards;
    private final List<BoardGroupResponse> boardGroups;
    private final List<TableFieldResponse> securityFields;
    private final List<TableFieldResponse> marketDataFields;
    private final List<TableFieldResponse> tradeFields;
    private final List<TableFieldResponse> orderBookFields;
    private final List<TableFieldResponse> historyFields;
    private final List<TableFieldResponse> tradeHistoryFields;
    private final List<TableFieldResponse> marketDataYieldFields;
    private final List<TableFieldResponse> tradeYieldFields;
    private final List<TableFieldResponse> historyYieldFields;
    private final List<TableFieldResponse> securityStatisticFields;

    /**
     * Создаёт неизменяемый составной ответ из секций описания рынка.
     *
     * @param boards режимы торгов
     * @param boardGroups группы режимов торгов
     * @param securityFields поля статических данных инструментов
     * @param marketDataFields поля текущих рыночных данных
     * @param tradeFields поля таблицы сделок
     * @param orderBookFields поля стакана
     * @param historyFields поля истории инструментов
     * @param tradeHistoryFields поля истории сделок
     * @param marketDataYieldFields поля текущих доходностей
     * @param tradeYieldFields поля доходностей сделок
     * @param historyYieldFields поля истории доходностей
     * @param securityStatisticFields поля статистики инструментов
     */
    @Builder
    public MarketInfoResponse(List<BoardResponse> boards,
                              List<BoardGroupResponse> boardGroups,
                              List<TableFieldResponse> securityFields,
                              List<TableFieldResponse> marketDataFields,
                              List<TableFieldResponse> tradeFields,
                              List<TableFieldResponse> orderBookFields,
                              List<TableFieldResponse> historyFields,
                              List<TableFieldResponse> tradeHistoryFields,
                              List<TableFieldResponse> marketDataYieldFields,
                              List<TableFieldResponse> tradeYieldFields,
                              List<TableFieldResponse> historyYieldFields,
                              List<TableFieldResponse> securityStatisticFields)
    {
        this.boards = ResponseLists.immutableCopy(boards);
        this.boardGroups = ResponseLists.immutableCopy(boardGroups);
        this.securityFields = ResponseLists.immutableCopy(securityFields);
        this.marketDataFields = ResponseLists.immutableCopy(marketDataFields);
        this.tradeFields = ResponseLists.immutableCopy(tradeFields);
        this.orderBookFields = ResponseLists.immutableCopy(orderBookFields);
        this.historyFields = ResponseLists.immutableCopy(historyFields);
        this.tradeHistoryFields = ResponseLists.immutableCopy(tradeHistoryFields);
        this.marketDataYieldFields = ResponseLists.immutableCopy(marketDataYieldFields);
        this.tradeYieldFields = ResponseLists.immutableCopy(tradeYieldFields);
        this.historyYieldFields = ResponseLists.immutableCopy(historyYieldFields);
        this.securityStatisticFields = ResponseLists.immutableCopy(securityStatisticFields);
    }
}
