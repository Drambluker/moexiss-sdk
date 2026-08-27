package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса рынков торговой системы. */
@Getter
@Setter
public class MarketsEngineParams
{
    private final String engine;

    private Language language = Language.RU;

    /**
     * @param engine код торговой системы, например {@code stock}
     */
    public MarketsEngineParams(String engine)
    {
        this.engine = engine;
    }
}
