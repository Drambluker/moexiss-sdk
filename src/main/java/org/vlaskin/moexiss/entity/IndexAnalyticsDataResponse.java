package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.TypedField;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

/**
 * Аналитические данные инструмента в составе индекса.
 */

@ToString(callSuper = true)
public class IndexAnalyticsDataResponse extends BasicEntity<IndexAnalyticsDataResponse.Fields>
{
    private IndexAnalyticsDataResponse()
    {
        super();
    }

    private IndexAnalyticsDataResponse(Map<Fields, Boolean> booleanFields,
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
     * Возвращает значение поля {@code IndexCode}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getIndexCode()
    {
        return stringFields.get(Fields.INDEX_CODE);
    }

    /**
     * Возвращает значение поля {@code SecurityCode}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getSecurityCode()
    {
        return stringFields.get(Fields.SECURITY_CODE);
    }

    /**
     * Возвращает значение поля {@code TradeDate}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public LocalDate getTradeDate()
    {
        return localDateFields.get(Fields.TRADE_DATE);
    }

    /**
     * Возвращает значение поля {@code TradeSessionDate}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public LocalDate getTradeSessionDate()
    {
        return localDateFields.get(Fields.TRADE_SESSION_DATE);
    }

    /**
     * Возвращает значение поля {@code Weight}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Double getWeight()
    {
        return doubleFields.get(Fields.WEIGHT);
    }

    /**
     * Возвращает значение поля {@code TradingSession}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public TradingSession getTradingSession()
    {
        Integer code = integerFields.get(Fields.TRADING_SESSION);
        return code == null ? null : TradingSession.getTradingSession(code);
    }

    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        INDEX_CODE(String.class),
        SECURITY_CODE(String.class),
        SECURITY_CODES(String.class),
        SHORTNAMES(String.class),
        TRADE_DATE(LocalDate.class),
        TRADE_SESSION_DATE(LocalDate.class),
        TRADING_SESSION(Integer.class),
        WEIGHT(Double.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("indexid", INDEX_CODE),
                Map.entry("secids", SECURITY_CODES),
                Map.entry("shortnames", SHORTNAMES),
                Map.entry("ticker", SECURITY_CODE),
                Map.entry("trade_session_date", TRADE_SESSION_DATE),
                Map.entry("tradedate", TRADE_DATE),
                Map.entry("tradingsession", TRADING_SESSION),
                Map.entry("weight", WEIGHT)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<IndexAnalyticsDataResponse, Fields>
    {
        @Override
        public IndexAnalyticsDataResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            IndexAnalyticsDataResponse entity = new IndexAnalyticsDataResponse();
            process(entity, jsonElement, columns, metadata);
            return new IndexAnalyticsDataResponse(
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
