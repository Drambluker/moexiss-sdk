package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class IndicesSecurityParams extends BaseParams
{
    private final String security;

    private Language language = DEFAULT_LANGUAGE;
    private boolean onlyActual = false;

    public IndicesSecurityParams(String security)
    {
        this.security = security;
    }
}
