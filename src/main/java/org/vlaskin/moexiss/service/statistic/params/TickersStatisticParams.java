package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;

/** Параметры запроса состава индекса за заданную дату. */
@Getter
@Setter
public class TickersStatisticParams
{
    private final String index;

    private TradingSession tradingSession = TradingSession.TOTAL;
    private LocalDate date;

    /** @param index код индекса */
    public TickersStatisticParams(String index)
    {
        this.index = index;
    }
}
