package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;

import java.time.LocalTime;

@Getter
@Builder
@ToString
public class TimeTableRecord
{
    private Integer weekDay;
    private Boolean workDay;
    private LocalTime startTime;
    private LocalTime stopTime;

    public static class Processor extends AbstractProcessor<TimeTableRecord>
    {
        @Override
        public TimeTableRecord processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            TimeTableRecordBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            TimeTableRecordBuilder timeTableRecordBuilder = (TimeTableRecordBuilder) builder;
            switch (name)
            {
                case "week_day" -> timeTableRecordBuilder.weekDay(value.getAsInt());
                case "is_work_day" -> timeTableRecordBuilder.workDay(value.getAsInt() == 1);
                case "start_time" -> timeTableRecordBuilder.startTime(LocalTime.parse(value.getAsString()));
                case "stop_time" -> timeTableRecordBuilder.stopTime(LocalTime.parse(value.getAsString()));
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
