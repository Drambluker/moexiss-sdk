package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class EngineInfo
{
    private Engine engine;
    private List<TimeTableRecord> timeTable;
    private List<DailyTableRecord> dailyTable;
}
