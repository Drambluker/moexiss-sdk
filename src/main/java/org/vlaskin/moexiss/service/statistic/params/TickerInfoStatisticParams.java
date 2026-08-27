package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;

/** Параметры истории веса отдельного инструмента в индексе. */
@Getter
@Setter
public class TickerInfoStatisticParams
{
    private final String index;
    private final String ticker;

    private Language language = Language.RU;
    private TradingSession tradingSession = TradingSession.TOTAL;
    private int startIndex = 0;
    private LocalDate from;
    private LocalDate till;

    /**
     * @param index код индекса
     * @param ticker код инструмента
     * @param from начальная дата периода включительно
     * @param till конечная дата периода включительно
     */
    public TickerInfoStatisticParams(String index, String ticker, LocalDate from, LocalDate till)
    {
        this.index = index;
        this.ticker = ticker;
        this.from = from;
        this.till = till;
    }
}
