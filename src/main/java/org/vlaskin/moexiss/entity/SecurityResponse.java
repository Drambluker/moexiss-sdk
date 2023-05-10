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
public class SecurityResponse extends BasicEntity<SecurityResponse.Fields>
{
    private SecurityResponse()
    {
        super();
    }

    private SecurityResponse(Map<Fields, Boolean> booleanFields,
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
        ACCRUED_INTEREST(Double.class),
        ANNUAL_HIGH(Double.class),
        ANNUAL_LOW(Double.class),
        BOARD_CODE(String.class),
        BOARD_NAME(String.class),
        BUYBACK_DATE(LocalDate.class),
        BUYBACK_PRICE(Double.class),
        CALC_MODE(String.class),
        CODE(String.class),
        COUPON_PERCENT(Double.class),
        COUPON_PERIOD(Integer.class),
        COUPON_VALUE(Double.class),
        CURRENCY_ID(String.class),
        DECIMALS(Integer.class),
        EMITENT_ID(Integer.class),
        EMITENT_INN(String.class),
        EMITENT_OKPO(String.class),
        EMITENT_TITLE(String.class),
        ENG_NAME(String.class),
        FACE_UNIT(String.class),
        FACE_VALUE(Double.class),
        GOS_REG(String.class),
        GROUP(String.class),
        ID(Integer.class),
        INSTR_ID(String.class),
        ISIN(String.class),
        ISSUE_SIZE(Long.class),
        ISSUE_SIZE_PLACED(Long.class),
        IS_TRADED(Boolean.class),
        LIST_LEVEL(Integer.class),
        LOT_SIZE(Integer.class),
        LOT_VALUE(Double.class),
        MARKET_CODE(String.class),
        MARKET_PRICE_BOARD_ID(String.class),
        MATURITY_DATE(LocalDate.class),
        MIN_STEP(Double.class),
        NAME(String.class),
        NEXT_COUPON(LocalDate.class),
        OFFER_DATE(LocalDate.class),
        PREV_DATE(LocalDate.class),
        PREV_LEGAL_CLOSE_PRICE(Double.class),
        PREV_PRICE(Double.class),
        PREV_WA_PRICE(Double.class),
        PRIMARY_BOARD_ID(String.class),
        REG_NUMBER(String.class),
        REMARKS(String.class),
        SECTOR_ID(String.class),
        SETTLEMENT_DATE(LocalDate.class),
        SHORTNAME(String.class),
        STATUS(String.class),
        TYPE(String.class),
        YIELD_AT_PREV_WA_PRICE(Double.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("ACCRUEDINT", ACCRUED_INTEREST),
                Map.entry("ANNUALHIGH", ANNUAL_HIGH),
                Map.entry("ANNUALLOW", ANNUAL_LOW),
                Map.entry("BOARDID", BOARD_CODE),
                Map.entry("BOARDNAME", BOARD_NAME),
                Map.entry("BUYBACKDATE", BUYBACK_DATE),
                Map.entry("BUYBACKPRICE", BUYBACK_PRICE),
                Map.entry("CALCMODE", CALC_MODE),
                Map.entry("COUPONPERCENT", COUPON_PERCENT),
                Map.entry("COUPONPERIOD", COUPON_PERIOD),
                Map.entry("COUPONVALUE", COUPON_VALUE),
                Map.entry("CURRENCYID", CURRENCY_ID),
                Map.entry("DECIMALS", DECIMALS),
                Map.entry("FACEUNIT", FACE_UNIT),
                Map.entry("FACEVALUE", FACE_VALUE),
                Map.entry("INSTRID", INSTR_ID),
                Map.entry("ISIN", ISIN),
                Map.entry("ISSUESIZE", ISSUE_SIZE),
                Map.entry("ISSUESIZEPLACED", ISSUE_SIZE_PLACED),
                Map.entry("LATNAME", ENG_NAME),
                Map.entry("LISTLEVEL", LIST_LEVEL),
                Map.entry("LOTSIZE", LOT_SIZE),
                Map.entry("LOTVALUE", LOT_VALUE),
                Map.entry("MARKETCODE", MARKET_CODE),
                Map.entry("MATDATE", MATURITY_DATE),
                Map.entry("MINSTEP", MIN_STEP),
                Map.entry("NAME", NAME),
                Map.entry("NEXTCOUPON", NEXT_COUPON),
                Map.entry("OFFERDATE", OFFER_DATE),
                Map.entry("PREVDATE", PREV_DATE),
                Map.entry("PREVLEGALCLOSEPRICE", PREV_LEGAL_CLOSE_PRICE),
                Map.entry("PREVPRICE", PREV_PRICE),
                Map.entry("PREVWAPRICE", PREV_WA_PRICE),
                Map.entry("REGNUMBER", REG_NUMBER),
                Map.entry("REMARKS", REMARKS),
                Map.entry("SECID", CODE),
                Map.entry("SECNAME", NAME),
                Map.entry("SECTORID", SECTOR_ID),
                Map.entry("SECTYPE", TYPE),
                Map.entry("SETTLEDATE", SETTLEMENT_DATE),
                Map.entry("SHORTNAME", SHORTNAME),
                Map.entry("STATUS", STATUS),
                Map.entry("YIELDATPREVWAPRICE", YIELD_AT_PREV_WA_PRICE),
                Map.entry("emitent_id", EMITENT_ID),
                Map.entry("emitent_inn", EMITENT_INN),
                Map.entry("emitent_okpo", EMITENT_OKPO),
                Map.entry("emitent_title", EMITENT_TITLE),
                Map.entry("gosreg", GOS_REG),
                Map.entry("group", GROUP),
                Map.entry("id", ID),
                Map.entry("is_traded", IS_TRADED),
                Map.entry("isin", ISIN),
                Map.entry("marketprice_boardid", MARKET_PRICE_BOARD_ID),
                Map.entry("name", NAME),
                Map.entry("primary_boardid", PRIMARY_BOARD_ID),
                Map.entry("regnumber", REG_NUMBER),
                Map.entry("secid", CODE),
                Map.entry("shortname", SHORTNAME),
                Map.entry("type", TYPE)
        );
    }

    public static class Processor extends AbstractProcessor<SecurityResponse, Fields>
    {
        @Override
        public SecurityResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            SecurityResponse entity = new SecurityResponse();
            process(entity, jsonElement, columns, metadata);
            return new SecurityResponse(
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
