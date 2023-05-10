package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.BaseParams;

import java.time.LocalDate;

@Getter
@Setter
public class TickerInfoStatisticParams extends BaseParams
{
    private final String index;
    private final String ticker;

    private Language language = DEFAULT_LANGUAGE;
    private TradingSession tradingSession = DEFAULT_TRADING_SESSION;
    private int startIndex = 0;
    private LocalDate from = LocalDate.of(1900, 1, 1);
    private LocalDate till = LocalDate.of(1900, 1, 1);

    public TickerInfoStatisticParams(String index, String ticker)
    {
        this.index = index;
        this.ticker = ticker;
    }
}
