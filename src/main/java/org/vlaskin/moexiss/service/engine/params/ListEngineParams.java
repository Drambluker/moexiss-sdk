package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class ListEngineParams extends BaseParams
{
    private Language language = DEFAULT_LANGUAGE;
}
