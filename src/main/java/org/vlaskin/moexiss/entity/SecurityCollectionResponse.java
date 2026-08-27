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
 * Коллекция инструментов из справочника MOEX ISS.
 */

@ToString(callSuper = true)
public class SecurityCollectionResponse extends BasicEntity<SecurityCollectionResponse.Fields>
{
    private SecurityCollectionResponse()
    {
        super();
    }

    private SecurityCollectionResponse(Map<Fields, Boolean> booleanFields,
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
        ID(Integer.class),
        NAME(String.class),
        SECURITY_GROUP_ID(Integer.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("id", ID),
                Map.entry("name", NAME),
                Map.entry("security_group_id", SECURITY_GROUP_ID),
                Map.entry("title", TITLE)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<SecurityCollectionResponse, Fields>
    {
        @Override
        public SecurityCollectionResponse processJsonElement(JsonElement jsonElement,
                                                             JsonArray columns,
                                                             JsonObject metadata)
        {
            SecurityCollectionResponse entity = new SecurityCollectionResponse();
            process(entity, jsonElement, columns, metadata);
            return new SecurityCollectionResponse(
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
