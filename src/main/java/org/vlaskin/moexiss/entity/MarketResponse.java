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
 * Рынок торговой системы MOEX ISS.
 */

@ToString(callSuper = true)
public class MarketResponse extends BasicEntity<MarketResponse.Fields>
{
    private MarketResponse()
    {
        super();
    }

    private MarketResponse(Map<Fields, Boolean> booleanFields,
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

    /**
     * Возвращает значение поля {@code Name}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getName()
    {
        return stringFields.get(Fields.NAME);
    }

    /**
     * Возвращает значение поля {@code Title}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getTitle()
    {
        return stringFields.get(Fields.TITLE);
    }

    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        ENGINE_ID(Integer.class),
        ENGINE_NAME(String.class),
        ENGINE_TITLE(String.class),
        HAS_CANDLES(Boolean.class),
        HAS_DELAY(Boolean.class),
        HAS_EXTRA_YIELDS(Boolean.class),
        HAS_HISTORY(Boolean.class),
        HAS_HISTORY_FILES(Boolean.class),
        HAS_HISTORY_TRADES_FILES(Boolean.class),
        HAS_ORDER_BOOK(Boolean.class),
        HAS_TRADES(Boolean.class),
        HAS_TRADING_SESSION(Boolean.class),
        ID(Integer.class),
        IS_OTC(Boolean.class),
        MARKETPLACE(String.class),
        MARKET_ID(Integer.class),
        NAME(String.class),
        TITLE(String.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("NAME", NAME),
                Map.entry("has_candles", HAS_CANDLES),
                Map.entry("has_delay", HAS_DELAY),
                Map.entry("has_extra_yields", HAS_EXTRA_YIELDS),
                Map.entry("has_history", HAS_HISTORY),
                Map.entry("has_history_files", HAS_HISTORY_FILES),
                Map.entry("has_history_trades_files", HAS_HISTORY_TRADES_FILES),
                Map.entry("has_orderbook", HAS_ORDER_BOOK),
                Map.entry("has_trades", HAS_TRADES),
                Map.entry("has_tradingsession", HAS_TRADING_SESSION),
                Map.entry("id", ID),
                Map.entry("is_otc", IS_OTC),
                Map.entry("market_id", MARKET_ID),
                Map.entry("market_name", NAME),
                Map.entry("market_title", TITLE),
                Map.entry("marketplace", MARKETPLACE),
                Map.entry("trade_engine_id", ENGINE_ID),
                Map.entry("trade_engine_name", ENGINE_NAME),
                Map.entry("trade_engine_title", ENGINE_TITLE),
                Map.entry("title", TITLE)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<MarketResponse, Fields>
    {
        @Override
        public MarketResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            MarketResponse entity = new MarketResponse();
            process(entity, jsonElement, columns, metadata);
            return new MarketResponse(
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
