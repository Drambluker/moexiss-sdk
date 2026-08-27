package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.GroupBy;
import org.vlaskin.moexiss.params.Language;

/**
 * Параметры поиска инструментов: страница, размер страницы, текст, рынок и группировка.
 * Нумерация страниц начинается с нуля; размер страницы по умолчанию равен 100.
 */
@Getter
@Setter
public class ListSecurityParams
{
    private Language language = Language.RU;
    private int pageIndex = 0;
    private int limit = 100;
    private String query;
    private String engine;
    private String market;
    private Boolean trading;
    private GroupBy groupBy;
    private String groupByFilter;
}
