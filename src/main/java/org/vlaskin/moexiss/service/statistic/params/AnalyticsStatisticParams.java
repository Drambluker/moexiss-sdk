package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;

/**
 * Параметры аналитики индекса за дату.
 *
 * <p>Если задано точное смещение {@code startIndex}, оно имеет приоритет над номером страницы.
 */
@Getter
@Setter
public class AnalyticsStatisticParams
{
    private final String index;

    private Language language = Language.RU;
    private int pageIndex = 0;
    private int limit = 20;
    private Integer startIndex;
    private TradingSession tradingSession = TradingSession.TOTAL;
    private LocalDate date;
    private String tickers;

    /** @param index код индекса */
    public AnalyticsStatisticParams(String index)
    {
        this.index = index;
    }
}
