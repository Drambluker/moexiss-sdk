package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса индексов, в состав которых входит инструмент. */
@Getter
@Setter
public class IndicesSecurityParams
{
    private final String security;

    private Language language = Language.RU;
    private boolean onlyActual = false;

    /** @param security код инструмента, например {@code SBER} */
    public IndicesSecurityParams(String security)
    {
        this.security = security;
    }
}
