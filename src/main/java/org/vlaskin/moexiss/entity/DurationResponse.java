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
 * Доступный интервал свечей.
 */

@ToString(callSuper = true)
public class DurationResponse extends BasicEntity<DurationResponse.Fields>
{
    private DurationResponse()
    {
        super();
    }

    private DurationResponse(Map<Fields, Boolean> booleanFields,
                             Map<Fields, Integer> integerFields,
                             Map<Fields, Long> longFields,
                             Map<Fields, Double> doubleFields,
                             Map<Fields, LocalDate> localDateFields,
                             Map<Fields, LocalTime> localTimeFields,
                             Map<Fields, LocalDateTime> localDateTimeFields,
                             Map<Fields, String> stringFields)
    {
        super(booleanFields, integerFields, longFields, doubleFields, localDateFields,
                localTimeFields, localDateTimeFields, stringFields);
    }

    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        DAYS(Integer.class),
        DURATION(Integer.class),
        HINT(String.class),
        INTERVAL(Integer.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("days", DAYS),
                Map.entry("duration", DURATION),
                Map.entry("hint", HINT),
                Map.entry("interval", INTERVAL),
                Map.entry("title", TITLE)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<DurationResponse, Fields>
    {
        @Override
        public DurationResponse processJsonElement(JsonElement jsonElement, JsonArray columns,
                                                   JsonObject metadata)
        {
            DurationResponse entity = new DurationResponse();
            process(entity, jsonElement, columns, metadata);
            return new DurationResponse(
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
        protected void processValue(BasicEntity<Fields> entity, JsonElement value, String name,
                                    String type) throws UnknownAttributeException
        {
            Fields field = Fields.byName.get(name);
            if (field == null)
                throw new UnknownAttributeException(getClass(), name);
            processValue(entity, field, value, type, field.getType());
        }
    }
}
