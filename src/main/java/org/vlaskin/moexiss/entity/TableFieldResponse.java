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
public class TableFieldResponse extends BasicEntity<TableFieldResponse.Fields>
{
    private TableFieldResponse()
    {
        super();
    }

    private TableFieldResponse(Map<Fields, Boolean> booleanFields,
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
        HAS_PERCENT(Boolean.class),
        ID(Integer.class),
        IS_HIDDEN(Boolean.class),
        IS_LINKED(Boolean.class),
        IS_ORDERED(Boolean.class),
        IS_SIGNED(Boolean.class),
        IS_SYSTEM(Boolean.class),
        NAME(String.class),
        PRECISION(Integer.class),
        SHORT_TITLE(String.class),
        TITLE(String.class),
        TREND_BY(Integer.class),
        TYPE(String.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("has_percent", HAS_PERCENT),
                Map.entry("id", ID),
                Map.entry("is_hidden", IS_HIDDEN),
                Map.entry("is_linked", IS_LINKED),
                Map.entry("is_ordered", IS_ORDERED),
                Map.entry("is_signed", IS_SIGNED),
                Map.entry("is_system", IS_SYSTEM),
                Map.entry("name", NAME),
                Map.entry("precision", PRECISION),
                Map.entry("short_title", SHORT_TITLE),
                Map.entry("title", TITLE),
                Map.entry("trend_by", TREND_BY),
                Map.entry("type", TYPE)
        );
    }

    public static class Processor extends AbstractProcessor<TableFieldResponse, Fields>
    {
        @Override
        public TableFieldResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            TableFieldResponse entity = new TableFieldResponse();
            process(entity, jsonElement, columns, metadata);
            return new TableFieldResponse(
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
