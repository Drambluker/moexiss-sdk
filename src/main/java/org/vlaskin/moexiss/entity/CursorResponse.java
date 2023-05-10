package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.entity.base.BasicEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

@ToString(callSuper = true)
public class CursorResponse extends BasicEntity<CursorResponse.Fields>
{
    private CursorResponse()
    {
        super();
    }

    private CursorResponse(Map<Fields, Boolean> booleanFields,
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

    @Getter
    public enum Fields
    {
        INDEX(Long.class),
        NEXT_DATE(LocalDate.class),
        PAGE_SIZE(Long.class),
        PREV_DATE(LocalDate.class),
        TOTAL(Long.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("INDEX", INDEX),
                Map.entry("NEXT_DATE", NEXT_DATE),
                Map.entry("PAGESIZE", PAGE_SIZE),
                Map.entry("PREV_DATE", PREV_DATE),
                Map.entry("TOTAL", TOTAL)
        );
    }

    public static class Processor extends AbstractProcessor<CursorResponse, Fields>
    {
        @Override
        public CursorResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            CursorResponse entity = new CursorResponse();
            process(entity, jsonElement, columns, metadata);
            return new CursorResponse(
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
            processValue(entity, field, value, type, field.getClazz());
        }
    }
}
