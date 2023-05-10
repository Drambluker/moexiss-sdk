package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.BaseParams;

import java.time.LocalDate;

@Getter
@Setter
public class TickersStatisticParams extends BaseParams
{
    private final String index;

    private TradingSession tradingSession = DEFAULT_TRADING_SESSION;
    private LocalDate date;

    public TickersStatisticParams(String index)
    {
        this.index = index;
    }
}
