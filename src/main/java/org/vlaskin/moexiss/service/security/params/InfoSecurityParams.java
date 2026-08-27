package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса спецификации инструмента и его режимов торгов. */
@Getter
@Setter
public class InfoSecurityParams
{
    private final String security;

    private Language language = Language.RU;
    private Integer startIndex;

    /** @param security код инструмента, например {@code SBER} */
    public InfoSecurityParams(String security)
    {
        this.security = security;
    }
}
