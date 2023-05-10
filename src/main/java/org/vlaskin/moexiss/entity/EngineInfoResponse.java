package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class EngineInfoResponse
{
    private EngineResponse engine;
    private List<TimeTableRecordResponse> timeTable;
    private List<DailyTableRecordResponse> dailyTable;
}
