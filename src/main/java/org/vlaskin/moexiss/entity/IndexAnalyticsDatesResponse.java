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
public class IndexAnalyticsDatesResponse extends BasicEntity<IndexAnalyticsDatesResponse.Fields>
{
    private IndexAnalyticsDatesResponse()
    {
        super();
    }

    private IndexAnalyticsDatesResponse(Map<Fields, Boolean> booleanFields,
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
        FROM(LocalDate.class),
        TILL(LocalDate.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("from", FROM),
                Map.entry("till", TILL)
        );
    }

    public static class Processor extends AbstractProcessor<IndexAnalyticsDatesResponse, Fields>
    {
        @Override
        public IndexAnalyticsDatesResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            IndexAnalyticsDatesResponse entity = new IndexAnalyticsDatesResponse();
            process(entity, jsonElement, columns, metadata);
            return new IndexAnalyticsDatesResponse(
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
