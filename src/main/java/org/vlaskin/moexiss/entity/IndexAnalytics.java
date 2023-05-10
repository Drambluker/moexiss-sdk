package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class IndexAnalytics
{
    private final List<IndexAnalyticsData> data;
    private final List<Cursor> cursors;
    private final List<IndexAnalyticsDates> dates;
}
