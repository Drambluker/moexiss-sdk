package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;

/** Параметры списка индексов с доступной аналитикой. */
@Getter
@Setter
public class IndicesStatisticParams
{
    private Language language = Language.RU;
    private TradingSession tradingSession = TradingSession.TOTAL;
    private String securityCollection;
}
