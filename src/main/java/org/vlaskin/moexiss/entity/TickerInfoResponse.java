package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Составной ответ с историей инструмента в индексе и данными курсора.
 */

@Getter
@ToString
public final class TickerInfoResponse
{
    private final List<TickerResponse> tickers;
    private final List<CursorResponse> cursors;

    /**
     * @param tickers история инструмента в индексе
     * @param cursors данные постраничного чтения
     */
    @Builder
    public TickerInfoResponse(List<TickerResponse> tickers, List<CursorResponse> cursors)
    {
        this.tickers = ResponseLists.immutableCopy(tickers);
        this.cursors = ResponseLists.immutableCopy(cursors);
    }
}
