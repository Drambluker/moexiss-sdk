package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/** Составной ответ с аналитикой индекса, курсором и доступным диапазоном дат. */
@Getter
@ToString
public final class IndexAnalyticsResponse
{
    private final List<IndexAnalyticsDataResponse> data;
    private final List<CursorResponse> cursors;
    private final List<IndexAnalyticsDatesResponse> dates;

    /**
     * @param data строки аналитики
     * @param cursors данные постраничного чтения
     * @param dates доступный интервал дат
     */
    @Builder
    public IndexAnalyticsResponse(List<IndexAnalyticsDataResponse> data,
                                  List<CursorResponse> cursors,
                                  List<IndexAnalyticsDatesResponse> dates)
    {
        this.data = ResponseLists.immutableCopy(data);
        this.cursors = ResponseLists.immutableCopy(cursors);
        this.dates = ResponseLists.immutableCopy(dates);
    }
}
