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
public class BoardResponse extends BasicEntity<BoardResponse.Fields>
{
    private BoardResponse()
    {
        super();
    }

    private BoardResponse(Map<Fields, Boolean> booleanFields,
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
        BOARD_GROUP_ID(Integer.class),
        CODE(String.class),
        CURRENCY_ID(String.class),
        DECIMALS(Integer.class),
        ENGINE(String.class),
        ENGINE_ID(Integer.class),
        HISTORY_FROM(LocalDate.class),
        HISTORY_TILL(LocalDate.class),
        ID(Integer.class),
        IS_PRIMARY(Boolean.class),
        IS_TRADED(Boolean.class),
        LISTED_FROM(LocalDate.class),
        LISTED_TILL(LocalDate.class),
        MARKET(String.class),
        MARKET_ID(Integer.class),
        SECURITY_CODE(String.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("board_group_id", BOARD_GROUP_ID),
                Map.entry("boardid", CODE),
                Map.entry("currencyid", CURRENCY_ID),
                Map.entry("decimals", DECIMALS),
                Map.entry("engine", ENGINE),
                Map.entry("engine_id", ENGINE_ID),
                Map.entry("history_from", HISTORY_FROM),
                Map.entry("history_till", HISTORY_TILL),
                Map.entry("id", ID),
                Map.entry("is_primary", IS_PRIMARY),
                Map.entry("is_traded", IS_TRADED),
                Map.entry("listed_from", LISTED_FROM),
                Map.entry("listed_till", LISTED_TILL),
                Map.entry("market", MARKET),
                Map.entry("market_id", MARKET_ID),
                Map.entry("secid", SECURITY_CODE),
                Map.entry("title", TITLE)
        );
    }

    public static class Processor extends AbstractProcessor<BoardResponse, Fields>
    {
        @Override
        public BoardResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            BoardResponse entity = new BoardResponse();
            process(entity, jsonElement, columns, metadata);
            return new BoardResponse(
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
