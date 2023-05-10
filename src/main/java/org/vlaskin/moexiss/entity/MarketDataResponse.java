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
public class MarketDataResponse extends BasicEntity<MarketDataResponse.Fields>
{
    private MarketDataResponse()
    {
        super();
    }

    private MarketDataResponse(Map<Fields, Boolean> booleanFields,
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
        String tradingSessionCode = stringFields.get(Fields.TRADING_SESSION);
        if (tradingSessionCode == null)
            return null;
        return TradingSession.getTradingSession(Integer.parseInt(tradingSessionCode));
    }

    /**
     * WA - Weighted Average
     */
    @Getter
    public enum Fields
    {
        BEI_CLOSE(Double.class),
        BID(Double.class),
        BID_DATE(LocalDateTime.class),
        BID_DEPTH(Integer.class),
        BID_DEPTH_T(Integer.class),
        BOARD_CODE(String.class),
        CAPITALIZATION(Double.class),
        CAPITALIZATION_USD(Double.class),
        CHANGE(Double.class),
        CLOSE_PRICE(Double.class),
        CLOSE_YIELD(Double.class),
        CLOSING_AUCTION_PRICE(Double.class),
        CLOSING_AUCTION_VOLUME(Double.class),
        CURRENT_VALUE(Double.class),
        DURATION(Double.class),
        ETF_SETTLE_CURRENCY(String.class),
        ETF_SETTLE_PRICE(Double.class),
        HIGH(Double.class),
        HIGH_BID(String.class),
        IRICPI_CLOSE(Double.class),
        ISSUE_CAPITALIZATION(Double.class),
        ISSUE_CAPITALIZATION_UPDATE_TIME(LocalTime.class),
        LAST(Double.class),
        LAST_BID(String.class),
        LAST_CHANGE(Double.class),
        LAST_CHANGE_BP(Double.class),
        LAST_CHANGE_PERCENT(Double.class),
        LAST_CHANGE_PRC(Double.class),
        LAST_CHANGE_TO_LAST_WA_PRICE(Double.class),
        LAST_CHANGE_TO_OPEN(Double.class),
        LAST_CHANGE_TO_OPEN_PRC(Double.class),
        LAST_OFFER(String.class),
        LAST_SETTLE_CODE(String.class),
        LAST_TO_PREV_PRICE(Double.class),
        LAST_VALUE(Double.class),
        LOW(Double.class),
        LOW_OFFER(String.class),
        L_CLOSE_PRICE(Double.class),
        L_CURRENT_PRICE(Double.class),
        MARKET_PRICE(Double.class),
        MARKET_PRICE_2(Double.class),
        MARKET_PRICE_TODAY(Double.class),
        MONTH_CHANGE_BP(Double.class),
        MONTH_CHANGE_PRC(Double.class),
        NUM_BIDS(Integer.class),
        NUM_OFFERS(Integer.class),
        NUM_TRADES(Integer.class),
        OFFER(Double.class),
        OFFER_DATE(LocalDateTime.class),
        OFFER_DEPTH(Integer.class),
        OFFER_DEPTH_T(Integer.class),
        OPEN(Double.class),
        OPEN_PERIOD_PRICE(Double.class),
        OPEN_VALUE(Double.class),
        PRICE_MINUS_PREV_WA_PRICE(Double.class),
        QTY(Long.class),
        SECURITY_CODE(String.class),
        SEQ_NUM(Long.class),
        SPREAD(Double.class),
        SYS_TIME(LocalDateTime.class),
        TIME(LocalTime.class),
        TRADE_DATE(LocalDate.class),
        TRADING_SESSION(String.class),
        TRADING_STATUS(String.class),
        UPDATE_TIME(LocalTime.class),
        VALUE(Double.class),
        VALUE_USD(Double.class),
        VAL_TODAY(Double.class),
        VAL_TODAY_RUR(Long.class),
        VAL_TODAY_USD(Double.class),
        VOL_TODAY(Double.class),
        WAP_TO_PREV_WA_PRICE(Double.class),
        WAP_TO_PREV_WA_PRICE_PERCENT(Double.class),
        WA_PRICE(Double.class),
        YEAR_CHANGE_BP(Double.class),
        YEAR_CHANGE_PRC(Double.class),
        YIELD(Double.class),
        YIELD_AT_WA_PRICE(Double.class),
        YIELD_LAST_COUPON(Double.class),
        YIELD_TO_OFFER(Double.class),
        YIELD_TO_PREV_YIELD(Double.class);

        private final Class<?> clazz;

        Fields(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("BEICLOSE", BEI_CLOSE),
                Map.entry("BID", BID),
                Map.entry("BIDDATE", BID_DATE),
                Map.entry("BIDDEPTH", BID_DEPTH),
                Map.entry("BIDDEPTHT", BID_DEPTH_T),
                Map.entry("BOARDID", BOARD_CODE),
                Map.entry("CAPITALIZATION", CAPITALIZATION),
                Map.entry("CAPITALIZATION_USD", CAPITALIZATION_USD),
                Map.entry("CHANGE", CHANGE),
                Map.entry("CLOSEPRICE", CLOSE_PRICE),
                Map.entry("CLOSEYIELD", CLOSE_YIELD),
                Map.entry("CLOSINGAUCTIONPRICE", CLOSING_AUCTION_PRICE),
                Map.entry("CLOSINGAUCTIONVOLUME", CLOSING_AUCTION_VOLUME),
                Map.entry("CURRENTVALUE", CURRENT_VALUE),
                Map.entry("DURATION", DURATION),
                Map.entry("ETFSETTLECURRENCY", ETF_SETTLE_CURRENCY),
                Map.entry("ETFSETTLEPRICE", ETF_SETTLE_PRICE),
                Map.entry("HIGH", HIGH),
                Map.entry("HIGHBID", HIGH_BID),
                Map.entry("IRICPICLOSE", IRICPI_CLOSE),
                Map.entry("ISSUECAPITALIZATION", ISSUE_CAPITALIZATION),
                Map.entry("ISSUECAPITALIZATION_UPDATETIME", ISSUE_CAPITALIZATION_UPDATE_TIME),
                Map.entry("LAST", LAST),
                Map.entry("LASTBID", LAST_BID),
                Map.entry("LASTCHANGE", LAST_CHANGE),
                Map.entry("LASTCHANGEBP", LAST_CHANGE_BP),
                Map.entry("LASTCHANGEPRC", LAST_CHANGE_PRC),
                Map.entry("LASTCHANGEPRCNT", LAST_CHANGE_PERCENT),
                Map.entry("LASTCHANGETOOPEN", LAST_CHANGE_TO_OPEN),
                Map.entry("LASTCHANGETOOPENPRC", LAST_CHANGE_TO_OPEN_PRC),
                Map.entry("LASTCNGTOLASTWAPRICE", LAST_CHANGE_TO_LAST_WA_PRICE),
                Map.entry("LASTOFFER", LAST_OFFER),
                Map.entry("LASTSETTLECODE", LAST_SETTLE_CODE),
                Map.entry("LASTTOPREVPRICE", LAST_TO_PREV_PRICE),
                Map.entry("LASTVALUE", LAST_VALUE),
                Map.entry("LCLOSEPRICE", L_CLOSE_PRICE),
                Map.entry("LCURRENTPRICE", L_CURRENT_PRICE),
                Map.entry("LOW", LOW),
                Map.entry("LOWOFFER", LOW_OFFER),
                Map.entry("MARKETPRICE", MARKET_PRICE),
                Map.entry("MARKETPRICE2", MARKET_PRICE_2),
                Map.entry("MARKETPRICETODAY", MARKET_PRICE_TODAY),
                Map.entry("MONTHCHANGEBP", MONTH_CHANGE_BP),
                Map.entry("MONTHCHANGEPRC", MONTH_CHANGE_PRC),
                Map.entry("NUMBIDS", NUM_BIDS),
                Map.entry("NUMOFFERS", NUM_OFFERS),
                Map.entry("NUMTRADES", NUM_TRADES),
                Map.entry("OFFER", OFFER),
                Map.entry("OFFERDATE", OFFER_DATE),
                Map.entry("OFFERDEPTH", OFFER_DEPTH),
                Map.entry("OFFERDEPTHT", OFFER_DEPTH_T),
                Map.entry("OPEN", OPEN),
                Map.entry("OPENPERIODPRICE", OPEN_PERIOD_PRICE),
                Map.entry("OPENVALUE", OPEN_VALUE),
                Map.entry("PRICEMINUSPREVWAPRICE", PRICE_MINUS_PREV_WA_PRICE),
                Map.entry("QTY", QTY),
                Map.entry("SECID", SECURITY_CODE),
                Map.entry("SEQNUM", SEQ_NUM),
                Map.entry("SPREAD", SPREAD),
                Map.entry("SYSTIME", SYS_TIME),
                Map.entry("TIME", TIME),
                Map.entry("TRADEDATE", TRADE_DATE),
                Map.entry("TRADINGSESSION", TRADING_SESSION),
                Map.entry("TRADINGSTATUS", TRADING_STATUS),
                Map.entry("UPDATETIME", UPDATE_TIME),
                Map.entry("VALTODAY", VAL_TODAY),
                Map.entry("VALTODAY_RUR", VAL_TODAY_RUR),
                Map.entry("VALTODAY_USD", VAL_TODAY_USD),
                Map.entry("VALUE", VALUE),
                Map.entry("VALUE_USD", VALUE_USD),
                Map.entry("VOLTODAY", VOL_TODAY),
                Map.entry("WAPRICE", WA_PRICE),
                Map.entry("WAPTOPREVWAPRICE", WAP_TO_PREV_WA_PRICE),
                Map.entry("WAPTOPREVWAPRICEPRCNT", WAP_TO_PREV_WA_PRICE_PERCENT),
                Map.entry("YEARCHANGEBP", YEAR_CHANGE_BP),
                Map.entry("YEARCHANGEPRC", YEAR_CHANGE_PRC),
                Map.entry("YIELD", YIELD),
                Map.entry("YIELDATWAPRICE", YIELD_AT_WA_PRICE),
                Map.entry("YIELDLASTCOUPON", YIELD_LAST_COUPON),
                Map.entry("YIELDTOOFFER", YIELD_TO_OFFER),
                Map.entry("YIELDTOPREVYIELD", YIELD_TO_PREV_YIELD)
        );
    }

    public static class Processor extends AbstractProcessor<MarketDataResponse, Fields>
    {
        @Override
        public MarketDataResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            MarketDataResponse entity = new MarketDataResponse();
            process(entity, jsonElement, columns, metadata);
            return new MarketDataResponse(
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
