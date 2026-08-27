package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/** Составной ответ с описанием торговой системы и её расписанием. */
@Getter
@ToString
public final class EngineInfoResponse
{
    private final EngineResponse engine;
    private final List<TimeTableRecordResponse> timeTable;
    private final List<DailyTableRecordResponse> dailyTable;

    /**
     * @param engine описание торговой системы; может отсутствовать в ответе
     * @param timeTable недельное расписание
     * @param dailyTable исключения из недельного расписания
     */
    @Builder
    public EngineInfoResponse(EngineResponse engine,
                              List<TimeTableRecordResponse> timeTable,
                              List<DailyTableRecordResponse> dailyTable)
    {
        this.engine = engine;
        this.timeTable = ResponseLists.immutableCopy(timeTable);
        this.dailyTable = ResponseLists.immutableCopy(dailyTable);
    }
}
