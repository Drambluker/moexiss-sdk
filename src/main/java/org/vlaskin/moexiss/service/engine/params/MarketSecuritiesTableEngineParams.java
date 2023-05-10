package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.Order;
import org.vlaskin.moexiss.service.BaseParams;

import java.util.Collection;

@Getter
@Setter
public class MarketSecuritiesTableEngineParams extends BaseParams
{
    protected final String engine;
    protected final String market;

    protected Language language = DEFAULT_LANGUAGE;
    protected int first = 0;
    protected String sortColumn;
    protected Order sortOrder = Order.ASC;
    protected boolean leaders = false;
    protected boolean nearest = false;
    protected boolean previousSession = false;
    protected String primaryBoard;
    protected Collection<String> assets;
    protected String index;
    protected Collection<String> securities;
    protected Collection<String> securityTypes;
    protected String securityCollection;

    public MarketSecuritiesTableEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }
}
