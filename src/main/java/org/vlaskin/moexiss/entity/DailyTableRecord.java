package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@ToString
public class DailyTableRecord
{
    private LocalDate date;
    private Boolean workDay;
    private LocalTime startTime;
    private LocalTime stopTime;

    public static class Processor extends AbstractProcessor<DailyTableRecord>
    {
        @Override
        public DailyTableRecord processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            DailyTableRecordBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            DailyTableRecordBuilder dailyTableRecordBuilder = (DailyTableRecordBuilder) builder;
            switch (name)
            {
                case "date" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        dailyTableRecordBuilder.date(LocalDate.parse(value.getAsString()));
                }
                case "is_work_day" -> dailyTableRecordBuilder.workDay(value.getAsInt() == 1);
                case "start_time" -> dailyTableRecordBuilder.startTime(LocalTime.parse(value.getAsString()));
                case "stop_time" -> dailyTableRecordBuilder.stopTime(LocalTime.parse(value.getAsString()));
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
