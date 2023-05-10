package org.vlaskin.moexiss.service.statistic.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class IndicesStatisticParams extends BaseParams
{
    private Language language = DEFAULT_LANGUAGE;
    private TradingSession tradingSession = DEFAULT_TRADING_SESSION;
    private String securityCollection;
}
