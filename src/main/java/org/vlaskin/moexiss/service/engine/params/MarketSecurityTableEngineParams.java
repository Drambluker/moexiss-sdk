package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketSecurityTableEngineParams extends MarketSecuritiesTableEngineParams
{
    private final String security;

    public MarketSecurityTableEngineParams(String engine, String market, String security)
    {
        super(engine, market);
        this.security = security;
    }
}
