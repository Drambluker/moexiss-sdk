package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса описания рынка и состава его таблиц. */
@Getter
@Setter
public class MarketInfoEngineParams
{
    private final String engine;
    private final String market;

    private Language language = Language.RU;

    /**
     * @param engine код торговой системы, например {@code stock}
     * @param market код рынка, например {@code shares}
     */
    public MarketInfoEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }
}
