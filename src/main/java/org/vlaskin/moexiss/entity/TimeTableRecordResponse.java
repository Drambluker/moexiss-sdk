package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.TypedField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

/**
 * Запись недельного расписания торговой системы.
 */

@ToString(callSuper = true)
public class TimeTableRecordResponse extends BasicEntity<TimeTableRecordResponse.Fields>
{
    private TimeTableRecordResponse()
    {
        super();
    }

    private TimeTableRecordResponse(Map<Fields, Boolean> booleanFields,
                                    Map<Fields, Integer> integerFields,
                                    Map<Fields, Long> longFields,
                                    Map<Fields, Double> doubleFields,
                                    Map<Fields, LocalDate> localDateFields,
                                    Map<Fields, LocalTime> localTimeFields,
                                    Map<Fields, LocalDateTime> localDateTimeFields,
                                    Map<Fields, String> stringFields)
    {
        super(booleanFields, integerFields, longFields, doubleFields, localDateFields, localTimeFields, localDateTimeFields, stringFields);
    }

    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        IS_WORK_DAY(Boolean.class),
        START_TIME(LocalTime.class),
        STOP_TIME(LocalTime.class),
        WEEK_DAY(Integer.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("is_work_day", IS_WORK_DAY),
                Map.entry("start_time", START_TIME),
                Map.entry("stop_time", STOP_TIME),
                Map.entry("week_day", WEEK_DAY)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<TimeTableRecordResponse, Fields>
    {
        @Override
        public TimeTableRecordResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            TimeTableRecordResponse entity = new TimeTableRecordResponse();
            process(entity, jsonElement, columns, metadata);
            return new TimeTableRecordResponse(
                    Collections.unmodifiableMap(entity.booleanFields),
                    Collections.unmodifiableMap(entity.integerFields),
                    Collections.unmodifiableMap(entity.longFields),
                    Collections.unmodifiableMap(entity.doubleFields),
                    Collections.unmodifiableMap(entity.localDateFields),
                    Collections.unmodifiableMap(entity.localTimeFields),
                    Collections.unmodifiableMap(entity.localDateTimeFields),
                    Collections.unmodifiableMap(entity.stringFields)
            );
        }

        @Override
        protected void processValue(BasicEntity<Fields> entity, JsonElement value, String name, String type) throws UnknownAttributeException
        {
            Fields field = Fields.byName.get(name);
            if (field == null)
                throw new UnknownAttributeException(getClass(), name);
            processValue(entity, field, value, type, field.getType());
        }
    }
}
