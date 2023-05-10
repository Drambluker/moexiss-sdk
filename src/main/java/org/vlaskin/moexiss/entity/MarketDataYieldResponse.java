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
public class MarketDataYieldResponse extends BasicEntity<MarketDataYieldResponse.Fields>
{
    private MarketDataYieldResponse()
    {
        super();
    }

    private MarketDataYieldResponse(Map<Fields, Boolean> booleanFields,
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
     * WA - Weighted Average
     */
    @Getter
    public enum Fields
    {
        BEI(Double.class),
        BOARD_CODE(String.class),
        DURATION(Integer.class),
        DURATION_WA_PRICE(Integer.class),
        EFFECTIVE_YIELD(Double.class),
        EFFECTIVE_YIELD_WA_PRICE(Double.class),
        G_SPREAD_BP(Integer.class),
        ICPI(Double.class),
        IR(Double.class),
        PRICE(Double.class),
        SECURITY_CODE(String.class),
        SEQ_NUM(Long.class),
        SYS_TIME(LocalDateTime.class),
        TRADE_MOMENT(LocalDateTime.class),
        WA_PRICE(Double.class),
        YIELD_DATE(LocalDate.class),
        YIELD_DATE_TYPE(String.class),
        YIELD_LAST_COUPON(Double.class),
        YIELD_TO_OFFER(Double.class),
        Z_CYC_MOMENT(LocalDateTime.class),
        Z_SPREAD_BP(Integer.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("BEI", BEI),
                Map.entry("BOARDID", BOARD_CODE),
                Map.entry("DURATION", DURATION),
                Map.entry("DURATIONWAPRICE", DURATION_WA_PRICE),
                Map.entry("EFFECTIVEYIELD", EFFECTIVE_YIELD),
                Map.entry("EFFECTIVEYIELDWAPRICE", EFFECTIVE_YIELD_WA_PRICE),
                Map.entry("GSPREADBP", G_SPREAD_BP),
                Map.entry("ICPI", ICPI),
                Map.entry("IR", IR),
                Map.entry("PRICE", PRICE),
                Map.entry("SECID", SECURITY_CODE),
                Map.entry("SEQNUM", SEQ_NUM),
                Map.entry("SYSTIME", SYS_TIME),
                Map.entry("TRADEMOMENT", TRADE_MOMENT),
                Map.entry("WAPRICE", WA_PRICE),
                Map.entry("YIELDDATE", YIELD_DATE),
                Map.entry("YIELDDATETYPE", YIELD_DATE_TYPE),
                Map.entry("YIELDLASTCOUPON", YIELD_LAST_COUPON),
                Map.entry("YIELDTOOFFER", YIELD_TO_OFFER),
                Map.entry("ZCYCMOMENT", Z_CYC_MOMENT),
                Map.entry("ZSPREADBP", Z_SPREAD_BP),
                Map.entry("boardid", BOARD_CODE),
                Map.entry("secid", SECURITY_CODE)
        );
    }

    public static class Processor extends AbstractProcessor<MarketDataYieldResponse, Fields>
    {
        @Override
        public MarketDataYieldResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            MarketDataYieldResponse entity = new MarketDataYieldResponse();
            process(entity, jsonElement, columns, metadata);
            return new MarketDataYieldResponse(
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
