package org.vlaskin.moexiss.service.security.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.GroupBy;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.service.BaseParams;

@Getter
@Setter
public class ListSecurityParams extends BaseParams
{
    private Language language = DEFAULT_LANGUAGE;
    private long pageIndex = 0L;
    private long limit = 100L;
    private String query;
    private String engine;
    private String market;
    private Boolean trading;
    private GroupBy groupBy;
    private String groupByFilter;
}
