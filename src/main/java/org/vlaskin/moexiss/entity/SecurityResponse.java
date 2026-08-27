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
 * Статические торговые данные инструмента.
 */

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
     * Возвращает значение поля {@code BoardCode}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getBoardCode()
    {
        return stringFields.get(Fields.BOARD_CODE);
    }

    /**
     * Возвращает значение поля {@code BondType}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getBondType()
    {
        return stringFields.get(Fields.BOND_TYPE);
    }

    /**
     * Возвращает значение поля {@code CallOptionDate}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public LocalDate getCallOptionDate()
    {
        return localDateFields.get(Fields.CALL_OPTION_DATE);
    }

    /**
     * Возвращает значение поля {@code Code}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getCode()
    {
        return stringFields.get(Fields.CODE);
    }

    /**
     * Возвращает значение поля {@code FaceValueOnSettlementDate}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Double getFaceValueOnSettlementDate()
    {
        return doubleFields.get(Fields.FACE_VALUE_ON_SETTLEMENT_DATE);
    }

    /**
     * Возвращает значение поля {@code Traded}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Boolean getTraded()
    {
        return booleanFields.get(Fields.IS_TRADED);
    }

    /**
     * Возвращает значение поля {@code LotSize}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Integer getLotSize()
    {
        return integerFields.get(Fields.LOT_SIZE);
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
     * Возвращает значение поля {@code PrevLegalClosePrice}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Double getPrevLegalClosePrice()
    {
        return doubleFields.get(Fields.PREV_LEGAL_CLOSE_PRICE);
    }

    /**
     * Возвращает значение поля {@code PrevPrice}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Double getPrevPrice()
    {
        return doubleFields.get(Fields.PREV_PRICE);
    }

    /**
     * Возвращает значение поля {@code PrevWaPrice}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public Double getPrevWaPrice()
    {
        return doubleFields.get(Fields.PREV_WA_PRICE);
    }

    /**
     * Возвращает значение поля {@code Status}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getStatus()
    {
        return stringFields.get(Fields.STATUS);
    }

    /**
     * WA - Weighted Average
     */
    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        ACCRUED_INTEREST(Double.class),
        ANNUAL_HIGH(Double.class),
        ANNUAL_LOW(Double.class),
        BOARD_CODE(String.class),
        BOARD_NAME(String.class),
        BOND_SUBTYPE(String.class),
        BOND_TYPE(String.class),
        BUYBACK_DATE(LocalDate.class),
        BUYBACK_PRICE(Double.class),
        CALL_OPTION_DATE(LocalDate.class),
        CALC_MODE(String.class),
        CODE(String.class),
        COUPON_PERCENT(Double.class),
        COUPON_PERIOD(Integer.class),
        COUPON_VALUE(Double.class),
        CURRENCY_ID(String.class),
        DATE_YIELD_FROM_ISSUER(LocalDate.class),
        DECIMALS(Integer.class),
        EMITENT_ID(Integer.class),
        EMITENT_INN(String.class),
        EMITENT_OKPO(String.class),
        EMITENT_TITLE(String.class),
        ENG_NAME(String.class),
        FACE_UNIT(String.class),
        FACE_VALUE(Double.class),
        FACE_VALUE_ON_SETTLEMENT_DATE(Double.class),
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
        PUT_OPTION_DATE(LocalDate.class),
        REG_NUMBER(String.class),
        REMARKS(String.class),
        SECTOR_ID(String.class),
        SETTLEMENT_DATE(LocalDate.class),
        SHORTNAME(String.class),
        STATUS(String.class),
        TYPE(String.class),
        YIELD_AT_PREV_WA_PRICE(Double.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("ACCRUEDINT", ACCRUED_INTEREST),
                Map.entry("ANNUALHIGH", ANNUAL_HIGH),
                Map.entry("ANNUALLOW", ANNUAL_LOW),
                Map.entry("BOARDID", BOARD_CODE),
                Map.entry("BOARDNAME", BOARD_NAME),
                Map.entry("BONDSUBTYPE", BOND_SUBTYPE),
                Map.entry("BONDTYPE", BOND_TYPE),
                Map.entry("BUYBACKDATE", BUYBACK_DATE),
                Map.entry("BUYBACKPRICE", BUYBACK_PRICE),
                Map.entry("CALLOPTIONDATE", CALL_OPTION_DATE),
                Map.entry("CALCMODE", CALC_MODE),
                Map.entry("COUPONPERCENT", COUPON_PERCENT),
                Map.entry("COUPONPERIOD", COUPON_PERIOD),
                Map.entry("COUPONVALUE", COUPON_VALUE),
                Map.entry("CURRENCYID", CURRENCY_ID),
                Map.entry("DATEYIELDFROMISSUER", DATE_YIELD_FROM_ISSUER),
                Map.entry("DECIMALS", DECIMALS),
                Map.entry("FACEUNIT", FACE_UNIT),
                Map.entry("FACEVALUE", FACE_VALUE),
                Map.entry("FACEVALUEONSETTLEDATE", FACE_VALUE_ON_SETTLEMENT_DATE),
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
                Map.entry("PUTOPTIONDATE", PUT_OPTION_DATE),
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

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<SecurityResponse, Fields>
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
            processValue(entity, field, value, type, field.getType());
        }
    }
}
