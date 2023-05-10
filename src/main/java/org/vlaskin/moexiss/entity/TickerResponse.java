package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

@ToString(callSuper = true)
public class TickerResponse extends BasicEntity<TickerResponse.Fields>
{
    private TickerResponse()
    {
        super();
    }

    private TickerResponse(Map<Fields, Boolean> booleanFields,
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

    public TradingSession getTradingSession()
    {
        return TradingSession.getTradingSession(integerFields.get(Fields.TRADING_SESSION));
    }

    /**
     * WA - Weighted Average
     */
    @Getter
    public enum Fields
    {
        CAP_INDEX(Double.class),
        CAP_TOTAL(Double.class),
        DETERMINAT(Double.class),
        FACTOR_A(Double.class),
        FACTOR_B(Double.class),
        FF_FACTOR(Double.class),
        FROM(LocalDate.class),
        INDEX_CODE(String.class),
        INFLUENCE(Double.class),
        ISSUE_SIZE_INDEX(Double.class),
        ISSUE_SIZE_TOTAL(Double.class),
        NUM_TRADES(Long.class),
        SECURITY_CODES(String.class),
        SHORTNAMES(String.class),
        TICKER(String.class),
        TILL(LocalDate.class),
        TRADE_DATE(LocalDate.class),
        TRADING_SESSION(Integer.class),
        VALUE(Double.class),
        VOLATILITY(Double.class),
        WA_PRICE(Double.class),
        WEIGHT(Double.class),
        W_FACTOR(Double.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("cap_index", CAP_INDEX),
                Map.entry("cap_total", CAP_TOTAL),
                Map.entry("determinat", DETERMINAT),
                Map.entry("factora", FACTOR_A),
                Map.entry("factorb", FACTOR_B),
                Map.entry("ff_factor", FF_FACTOR),
                Map.entry("from", FROM),
                Map.entry("indexid", INDEX_CODE),
                Map.entry("influence", INFLUENCE),
                Map.entry("issue_size_index", ISSUE_SIZE_INDEX),
                Map.entry("issue_size_total", ISSUE_SIZE_TOTAL),
                Map.entry("num_trades", NUM_TRADES),
                Map.entry("secids", SECURITY_CODES),
                Map.entry("shortnames", SHORTNAMES),
                Map.entry("ticker", TICKER),
                Map.entry("till", TILL),
                Map.entry("tradedate", TRADE_DATE),
                Map.entry("tradingsession", TRADING_SESSION),
                Map.entry("value", VALUE),
                Map.entry("volatility", VOLATILITY),
                Map.entry("w_factor", W_FACTOR),
                Map.entry("waprice", WA_PRICE),
                Map.entry("weight", WEIGHT)
        );
    }

    public static class Processor extends AbstractProcessor<TickerResponse, Fields>
    {
        @Override
        public TickerResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            TickerResponse entity = new TickerResponse();
            process(entity, jsonElement, columns, metadata);
            return new TickerResponse(
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
