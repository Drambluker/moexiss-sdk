package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class IndexAnalyticsResponse
{
    private final List<IndexAnalyticsDataResponse> data;
    private final List<CursorResponse> cursors;
    private final List<IndexAnalyticsDatesResponse> dates;
}
