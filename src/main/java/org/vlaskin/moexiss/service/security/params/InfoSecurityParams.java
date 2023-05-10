package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class InfoSecurityParams extends BaseParams
{
    private final String security;

    private Language language = DEFAULT_LANGUAGE;
    private Integer startIndex;

    public InfoSecurityParams(String security)
    {
        this.security = security;
    }
}
