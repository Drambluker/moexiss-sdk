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
 * Группа режимов торгов из справочника MOEX ISS.
 */

@ToString(callSuper = true)
public class BoardGroupResponse extends BasicEntity<BoardGroupResponse.Fields>
{
    private BoardGroupResponse()
    {
        super();
    }

    private BoardGroupResponse(Map<Fields, Boolean> booleanFields,
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
        BOARD_GROUP_ID(Integer.class),
        CATEGORY(String.class),
        ENGINE_ID(Integer.class),
        ENGINE_NAME(String.class),
        ENGINE_TITLE(String.class),
        ID(Integer.class),
        IS_DEFAULT(Boolean.class),
        IS_ORDER_DRIVEN(Boolean.class),
        IS_TRADED(Boolean.class),
        MARKET_ID(Integer.class),
        MARKET_NAME(String.class),
        NAME(String.class),
        SLUG(String.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("board_group_id", BOARD_GROUP_ID),
                Map.entry("category", CATEGORY),
                Map.entry("id", ID),
                Map.entry("is_default", IS_DEFAULT),
                Map.entry("is_order_driven", IS_ORDER_DRIVEN),
                Map.entry("is_traded", IS_TRADED),
                Map.entry("market_id", MARKET_ID),
                Map.entry("market_name", MARKET_NAME),
                Map.entry("name", NAME),
                Map.entry("slug", SLUG),
                Map.entry("trade_engine_id", ENGINE_ID),
                Map.entry("trade_engine_name", ENGINE_NAME),
                Map.entry("trade_engine_title", ENGINE_TITLE),
                Map.entry("title", TITLE)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<BoardGroupResponse, Fields>
    {
        @Override
        public BoardGroupResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            BoardGroupResponse entity = new BoardGroupResponse();
            process(entity, jsonElement, columns, metadata);
            return new BoardGroupResponse(
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
