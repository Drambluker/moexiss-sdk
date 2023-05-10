package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class MarketInfoEngineParams extends BaseParams
{
    private final String engine;
    private final String market;

    private Language language = DEFAULT_LANGUAGE;

    public MarketInfoEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }
}
