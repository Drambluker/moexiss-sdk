package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class BoardsEngineParams extends BaseParams
{
    protected final String engine;
    protected final String market;

    protected Language language = DEFAULT_LANGUAGE;

    public BoardsEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }
}
