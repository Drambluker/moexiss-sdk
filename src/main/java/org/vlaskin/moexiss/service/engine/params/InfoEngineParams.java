package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса описания и расписания торговой системы. */
@Getter
@Setter
public class InfoEngineParams
{
    private final String engine;

    private Language language = Language.RU;

    /**
     * @param engine код торговой системы, например {@code stock}
     */
    public InfoEngineParams(String engine)
    {
        this.engine = engine;
    }
}
