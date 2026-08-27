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
 * Тип инструмента из справочника MOEX ISS.
 */

@ToString(callSuper = true)
public class SecurityTypeResponse extends BasicEntity<SecurityTypeResponse.Fields>
{
    private SecurityTypeResponse()
    {
        super();
    }

    private SecurityTypeResponse(Map<Fields, Boolean> booleanFields,
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
        ENGINE_ID(Integer.class),
        ENGINE_NAME(String.class),
        ENGINE_TITLE(String.class),
        GROUP_NAME(String.class),
        ID(Integer.class),
        NAME(String.class),
        STOCK_TYPE(String.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("id", ID),
                Map.entry("security_group_name", GROUP_NAME),
                Map.entry("security_type_name", NAME),
                Map.entry("security_type_title", TITLE),
                Map.entry("stock_type", STOCK_TYPE),
                Map.entry("trade_engine_id", ENGINE_ID),
                Map.entry("trade_engine_name", ENGINE_NAME),
                Map.entry("trade_engine_title", ENGINE_TITLE)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<SecurityTypeResponse, Fields>
    {
        @Override
        public SecurityTypeResponse processJsonElement(JsonElement jsonElement, JsonArray columns,
                                                       JsonObject metadata)
        {
            SecurityTypeResponse entity = new SecurityTypeResponse();
            process(entity, jsonElement, columns, metadata);
            return new SecurityTypeResponse(
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
