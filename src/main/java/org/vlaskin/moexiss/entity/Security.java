package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Security
{
    private final Integer id;
    private final String securityId;
    private final String boardId;
    private final String shortname;
    private final Double annualHigh;
    private final Double annualLow;
    private final Double previousPrice;
    private final Integer lotSize;
    private final Double faceValue;
    private final String status;
    private final String boardName;
    private final Integer decimals;
    private final String name;
    private final String remarks;
    private final String marketCode;
    private final String instrId;
    private final String sectorId;
    private final Double minStep;
    private final Double previousWeightedAveragePrice;
    private final Double previousWeightedAveragePriceYield;
    private final Double couponValue;
    private final String faceUnit;
    private final LocalDate previousDate;
    private final LocalDate nextCouponDate;
    private final Integer couponPeriod;
    private final Double accruedInterest;
    private final Double couponPercent;
    private final Double buybackPrice;
    private final LocalDate buybackDate;
    private final Long issueSize;
    private final Long issueSizePlaced;
    private final String isin;
    private final String engName;
    private final Double previousLegalClosePrice;
    private final String regNumber;
    private final Boolean traded;
    private final Integer emitentId;
    private final String emitentTitle;
    private final String emitentInn;
    private final String emitentOkpo;
    private final String gosreg;
    private final String currencyId;
    private final String type;
    private final String group;
    private final String primaryBoardId;
    private final String marketPriceBoardId;
    private final Integer listLevel;
    private final LocalDate maturityDate;
    private final LocalDate offerDate;
    private final LocalDate settlementDate;
    private final String calculationMode;
    private final Double lotValue;

    public static class Processor extends AbstractProcessor<Security>
    {
        @Override
        public Security processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            SecurityBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            SecurityBuilder securityBuilder = (SecurityBuilder) builder;
            switch (name)
            {
                case "id" -> securityBuilder.id(value.getAsInt());
                case "secid", "SECID" -> securityBuilder.securityId(value.getAsString());
                case "BOARDID" -> securityBuilder.boardId(value.getAsString());
                case "shortname", "SHORTNAME" -> securityBuilder.shortname(value.getAsString());
                case "ANNUALHIGH" -> securityBuilder.annualHigh(value.getAsDouble());
                case "ANNUALLOW" -> securityBuilder.annualLow(value.getAsDouble());
                case "PREVPRICE" -> securityBuilder.previousPrice(value.getAsDouble());
                case "LOTSIZE" -> securityBuilder.lotSize(value.getAsInt());
                case "FACEVALUE" -> securityBuilder.faceValue(value.getAsDouble());
                case "STATUS" -> securityBuilder.status(value.getAsString());
                case "BOARDNAME" -> securityBuilder.boardName(value.getAsString());
                case "DECIMALS" -> securityBuilder.decimals(value.getAsInt());
                case "name", "NAME", "SECNAME" -> securityBuilder.name(value.getAsString());
                case "REMARKS" -> securityBuilder.remarks(value.getAsString());
                case "MARKETCODE" -> securityBuilder.marketCode(value.getAsString());
                case "INSTRID" -> securityBuilder.instrId(value.getAsString());
                case "SECTORID" -> securityBuilder.sectorId(value.getAsString());
                case "MINSTEP" -> securityBuilder.minStep(value.getAsDouble());
                case "PREVWAPRICE" -> securityBuilder.previousWeightedAveragePrice(value.getAsDouble());
                case "YIELDATPREVWAPRICE" -> securityBuilder.previousWeightedAveragePriceYield(value.getAsDouble());
                case "COUPONVALUE" -> securityBuilder.couponValue(value.getAsDouble());
                case "FACEUNIT" -> securityBuilder.faceUnit(value.getAsString());
                case "PREVDATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.previousDate(LocalDate.parse(value.getAsString()));
                }
                case "NEXTCOUPON" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.nextCouponDate(LocalDate.parse(value.getAsString()));
                }
                case "COUPONPERIOD" -> securityBuilder.couponPeriod(value.getAsInt());
                case "ACCRUEDINT" -> securityBuilder.accruedInterest(value.getAsDouble());
                case "COUPONPERCENT" -> securityBuilder.couponPercent(value.getAsDouble());
                case "BUYBACKPRICE" -> securityBuilder.buybackPrice(value.getAsDouble());
                case "BUYBACKDATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.buybackDate(LocalDate.parse(value.getAsString()));
                }
                case "ISSUESIZE" -> securityBuilder.issueSize(value.getAsLong());
                case "ISSUESIZEPLACED" -> securityBuilder.issueSizePlaced(value.getAsLong());
                case "isin", "ISIN" -> securityBuilder.isin(value.getAsString());
                case "LATNAME" -> securityBuilder.engName(value.getAsString());
                case "regnumber", "REGNUMBER" -> securityBuilder.regNumber(value.getAsString());
                case "PREVLEGALCLOSEPRICE" -> securityBuilder.previousLegalClosePrice(value.getAsDouble());
                case "is_traded" -> securityBuilder.traded(value.getAsInt() == 1);
                case "emitent_id" -> securityBuilder.emitentId(value.getAsInt());
                case "emitent_title" -> securityBuilder.emitentTitle(value.getAsString());
                case "emitent_inn" -> securityBuilder.emitentInn(value.getAsString());
                case "emitent_okpo" -> securityBuilder.emitentOkpo(value.getAsString());
                case "gosreg" -> securityBuilder.gosreg(value.getAsString());
                case "CURRENCYID" -> securityBuilder.currencyId(value.getAsString());
                case "type", "SECTYPE" -> securityBuilder.type(value.getAsString());
                case "group" -> securityBuilder.group(value.getAsString());
                case "primary_boardid" -> securityBuilder.primaryBoardId(value.getAsString());
                case "marketprice_boardid" -> securityBuilder.marketPriceBoardId(value.getAsString());
                case "LISTLEVEL" -> securityBuilder.listLevel(value.getAsInt());
                case "MATDATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.maturityDate(LocalDate.parse(value.getAsString()));
                }
                case "OFFERDATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.offerDate(LocalDate.parse(value.getAsString()));
                }
                case "SETTLEDATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        securityBuilder.settlementDate(LocalDate.parse(value.getAsString()));
                }
                case "CALCMODE" -> securityBuilder.calculationMode(value.getAsString());
                case "LOTVALUE" -> securityBuilder.lotValue(value.getAsDouble());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
