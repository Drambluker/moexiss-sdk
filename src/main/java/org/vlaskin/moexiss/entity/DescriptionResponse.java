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
public class DescriptionResponse extends BasicEntity<DescriptionResponse.Fields>
{
    private DescriptionResponse()
    {
        super();
    }

    private DescriptionResponse(Map<Fields, Boolean> booleanFields,
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
        IS_HIDDEN(Boolean.class),
        NAME(String.class),
        PRECISION(Long.class),
        SORT_ORDER(Long.class),
        TITLE(String.class),
        TYPE(String.class),
        VALUE(String.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("is_hidden", IS_HIDDEN),
                Map.entry("name", NAME),
                Map.entry("precision", PRECISION),
                Map.entry("sort_order", SORT_ORDER),
                Map.entry("title", TITLE),
                Map.entry("type", TYPE),
                Map.entry("value", VALUE)
        );
    }

    public static class Processor extends AbstractProcessor<DescriptionResponse, Fields>
    {
        @Override
        public DescriptionResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            DescriptionResponse entity = new DescriptionResponse();
            process(entity, jsonElement, columns, metadata);
            return new DescriptionResponse(
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
