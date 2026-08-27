package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса списка торговых систем; язык по умолчанию — русский. */
@Getter
@Setter
public class ListEngineParams
{
    private Language language = Language.RU;
}
