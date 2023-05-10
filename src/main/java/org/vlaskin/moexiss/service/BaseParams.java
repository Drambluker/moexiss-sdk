package org.vlaskin.moexiss.service;

import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.TradingSession;

public abstract class BaseParams
{
    protected static final Language DEFAULT_LANGUAGE = Language.RU;
    protected static final TradingSession DEFAULT_TRADING_SESSION = TradingSession.TOTAL;
}
