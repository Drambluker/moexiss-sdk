package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.BaseParams;

import java.time.LocalDate;

@Getter
@Setter
public class AnalyticsStatisticParams extends BaseParams
{
    private final String index;

    private Language language = DEFAULT_LANGUAGE;
    private int pageIndex = 0;
    private int limit = 20;
    private TradingSession tradingSession = DEFAULT_TRADING_SESSION;
    private LocalDate date;
    private String tickers;

    public AnalyticsStatisticParams(String index)
    {
        this.index = index;
    }
}
