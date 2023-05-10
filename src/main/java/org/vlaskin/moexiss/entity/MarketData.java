package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.params.TradingSession;
import org.vlaskin.moexiss.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@ToString
public class MarketData
{
    private final String securityId;
    private final String boardId;
    private final Double bid;
    private final String bidDepth;
    private final Double offer;
    private final String offerDepth;
    private final Double spread;
    private final Integer bidDepthT;
    private final Integer offerDepthT;
    private final Double open;
    private final Double low;
    private final Double high;
    private final Double last;
    private final Double lastChange;
    private final Double lastChangePercent;
    private final Integer quantity;
    private final Double value;
    private final Double valueUsd;
    private final Double weightedAveragePrice;
    private final Double lastChangeToLastWeightedAveragePrice;
    private final Double weightedAveragePriceChangeToPreviousPercent;
    private final Double weightedAveragePriceChangeToPrevious;
    private final Double closePrice;
    private final Double marketPriceToday;
    private final Double marketPrice;
    private final Double lastToPreviousPrice;
    private final Integer numTrades;
    private final Long volToday;
    private final Long valToday;
    private final Long valTodayUsd;
    private final Double etfSettlementPrice;
    private final String tradingStatus;
    private final LocalTime updateTime;
    private final String lastBid;
    private final String lastOffer;
    private final Double legalClosePrice;
    private final Double legalCurrentPrice;
    private final Double marketPrice2;
    private final String numBids;
    private final String numOffers;
    private final Double change;
    private final LocalTime time;
    private final String highBid;
    private final String lowOffer;
    private final Double priceMinusPreviousWeightedAveragePrice;
    private final Double openPeriodPrice;
    private final Long sequenceNumber;
    private final LocalDateTime systemTime;
    private final Double closingAuctionPrice;
    private final Double closingAuctionVolume;
    private final Double issueCapitalization;
    private final LocalTime issueCapitalizationUpdateTime;
    private final String etfSettlementCurrency;
    private final Long valTodayRur;
    private final TradingSession tradingSession;

    public static class Processor extends AbstractProcessor<MarketData>
    {
        @Override
        public MarketData processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            MarketDataBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            MarketDataBuilder marketDataBuilder = (MarketDataBuilder) builder;
            switch (name)
            {
                case "SECID" -> marketDataBuilder.securityId(value.getAsString());
                case "BOARDID" -> marketDataBuilder.boardId(value.getAsString());
                case "BID" -> marketDataBuilder.bid(value.getAsDouble());
                case "BIDDEPTH" -> marketDataBuilder.bidDepth(value.getAsString());
                case "OFFER" -> marketDataBuilder.offer(value.getAsDouble());
                case "OFFERDEPTH" -> marketDataBuilder.offerDepth(value.getAsString());
                case "SPREAD" -> marketDataBuilder.spread(value.getAsDouble());
                case "BIDDEPTHT" -> marketDataBuilder.bidDepthT(value.getAsInt());
                case "OFFERDEPTHT" -> marketDataBuilder.offerDepthT(value.getAsInt());
                case "OPEN" -> marketDataBuilder.open(value.getAsDouble());
                case "LOW" -> marketDataBuilder.low(value.getAsDouble());
                case "HIGH" -> marketDataBuilder.high(value.getAsDouble());
                case "LAST" -> marketDataBuilder.last(value.getAsDouble());
                case "LASTCHANGE" -> marketDataBuilder.lastChange(value.getAsDouble());
                case "LASTCHANGEPRCNT" -> marketDataBuilder.lastChangePercent(value.getAsDouble());
                case "QTY" -> marketDataBuilder.quantity(value.getAsInt());
                case "VALUE" -> marketDataBuilder.value(value.getAsDouble());
                case "VALUE_USD" -> marketDataBuilder.valueUsd(value.getAsDouble());
                case "WAPRICE" -> marketDataBuilder.weightedAveragePrice(value.getAsDouble());
                case "LASTCNGTOLASTWAPRICE" -> marketDataBuilder.lastChangeToLastWeightedAveragePrice(value.getAsDouble());
                case "WAPTOPREVWAPRICEPRCNT" -> marketDataBuilder.weightedAveragePriceChangeToPreviousPercent(value.getAsDouble());
                case "WAPTOPREVWAPRICE" -> marketDataBuilder.weightedAveragePriceChangeToPrevious(value.getAsDouble());
                case "CLOSEPRICE" -> marketDataBuilder.closePrice(value.getAsDouble());
                case "MARKETPRICETODAY" -> marketDataBuilder.marketPriceToday(value.getAsDouble());
                case "MARKETPRICE" -> marketDataBuilder.marketPrice(value.getAsDouble());
                case "LASTTOPREVPRICE" -> marketDataBuilder.lastToPreviousPrice(value.getAsDouble());
                case "NUMTRADES" -> marketDataBuilder.numTrades(value.getAsInt());
                case "VOLTODAY" -> marketDataBuilder.volToday(value.getAsLong());
                case "VALTODAY" -> marketDataBuilder.valToday(value.getAsLong());
                case "VALTODAY_USD" -> marketDataBuilder.valTodayUsd(value.getAsLong());
                case "ETFSETTLEPRICE" -> marketDataBuilder.etfSettlementPrice(value.getAsDouble());
                case "TRADINGSTATUS" -> marketDataBuilder.tradingStatus(value.getAsString());
                case "UPDATETIME" -> marketDataBuilder.updateTime(LocalTime.parse(value.getAsString()));
                case "LASTBID" -> marketDataBuilder.lastBid(value.getAsString());
                case "LASTOFFER" -> marketDataBuilder.lastOffer(value.getAsString());
                case "LCLOSEPRICE" -> marketDataBuilder.legalClosePrice(value.getAsDouble());
                case "LCURRENTPRICE" -> marketDataBuilder.legalCurrentPrice(value.getAsDouble());
                case "MARKETPRICE2" -> marketDataBuilder.marketPrice2(value.getAsDouble());
                case "NUMBIDS" -> marketDataBuilder.numBids(value.getAsString());
                case "NUMOFFERS" -> marketDataBuilder.numOffers(value.getAsString());
                case "CHANGE" -> marketDataBuilder.change(value.getAsDouble());
                case "TIME" -> marketDataBuilder.time(LocalTime.parse(value.getAsString()));
                case "HIGHBID" -> marketDataBuilder.highBid(value.getAsString());
                case "LOWOFFER" -> marketDataBuilder.lowOffer(value.getAsString());
                case "PRICEMINUSPREVWAPRICE" -> marketDataBuilder.priceMinusPreviousWeightedAveragePrice(value.getAsDouble());
                case "OPENPERIODPRICE" -> marketDataBuilder.openPeriodPrice(value.getAsDouble());
                case "SEQNUM" -> marketDataBuilder.sequenceNumber(value.getAsLong());
                case "SYSTIME" -> marketDataBuilder.systemTime(LocalDateTime.parse(value.getAsString(), DateUtils.DATE_TIME_FORMATTER));
                case "CLOSINGAUCTIONPRICE" -> marketDataBuilder.closingAuctionPrice(value.getAsDouble());
                case "CLOSINGAUCTIONVOLUME" -> marketDataBuilder.closingAuctionVolume(value.getAsDouble());
                case "ISSUECAPITALIZATION" -> marketDataBuilder.issueCapitalization(value.getAsDouble());
                case "ISSUECAPITALIZATION_UPDATETIME" -> marketDataBuilder.issueCapitalizationUpdateTime(LocalTime.parse(value.getAsString()));
                case "ETFSETTLECURRENCY" -> marketDataBuilder.etfSettlementCurrency(value.getAsString());
                case "VALTODAY_RUR" -> marketDataBuilder.valTodayRur(value.getAsLong());
                case "TRADINGSESSION" -> marketDataBuilder.tradingSession(TradingSession.getTradingSession(value.getAsInt()));
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
