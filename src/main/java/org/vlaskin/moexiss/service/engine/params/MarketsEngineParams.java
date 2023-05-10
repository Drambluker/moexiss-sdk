package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class MarketsEngineParams extends BaseParams
{
    private final String engine;

    private Language language = DEFAULT_LANGUAGE;

    public MarketsEngineParams(String engine)
    {
        this.engine = engine;
    }
}
