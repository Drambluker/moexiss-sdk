package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

/** Параметры таблицы одного инструмента на выбранном рынке. */
@Getter
@Setter
public class MarketSecurityTableEngineParams extends MarketSecuritiesTableEngineParams
{
    private final String security;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     * @param security код инструмента
     */
    public MarketSecurityTableEngineParams(String engine, String market, String security)
    {
        super(engine, market);
        this.security = security;
    }
}
