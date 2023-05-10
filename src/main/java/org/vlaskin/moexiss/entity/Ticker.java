package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.params.TradingSession;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Ticker
{
    private String indexId;
    private LocalDate tradeDate;
    private String ticker;
    private String shortname;
    private String secId;
    private Double waPrice;
    private Double issueSizeTotal;
    private Double capTotal;
    private Double ffFactor;
    private Double wFactor;
    private Double issueSizeIndex;
    private Double capIndex;
    private Double weight;
    private Double value;
    private Long numTrades;
    private Double volatility;
    private Double factorA;
    private Double factorB;
    private Double influence;
    private Double determinat;
    private LocalDate from;
    private LocalDate till;
    private TradingSession tradingSession;

    public static class Processor extends AbstractProcessor<Ticker>
    {
        @Override
        public Ticker processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            TickerBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            TickerBuilder tickerBuilder = (TickerBuilder) builder;
            switch (name)
            {
                case "indexid" -> tickerBuilder.indexId(value.getAsString());
                case "tradedate" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        tickerBuilder.tradeDate(LocalDate.parse(value.getAsString()));
                }
                case "ticker" -> tickerBuilder.ticker(value.getAsString());
                case "shortnames" -> tickerBuilder.shortname(value.getAsString());
                case "secids" -> tickerBuilder.secId(value.getAsString());
                case "waprice" -> tickerBuilder.waPrice(value.getAsDouble());
                case "issue_size_total" -> tickerBuilder.issueSizeTotal(value.getAsDouble());
                case "cap_total" -> tickerBuilder.capTotal(value.getAsDouble());
                case "ff_factor" -> tickerBuilder.ffFactor(value.getAsDouble());
                case "w_factor" -> tickerBuilder.wFactor(value.getAsDouble());
                case "issue_size_index" -> tickerBuilder.issueSizeIndex(value.getAsDouble());
                case "cap_index" -> tickerBuilder.capIndex(value.getAsDouble());
                case "weight" -> tickerBuilder.weight(value.getAsDouble());
                case "value" -> tickerBuilder.value(value.getAsDouble());
                case "num_trades" -> tickerBuilder.numTrades(value.getAsLong());
                case "volatility" -> tickerBuilder.volatility(value.getAsDouble());
                case "factora" -> tickerBuilder.factorA(value.getAsDouble());
                case "factorb" -> tickerBuilder.factorB(value.getAsDouble());
                case "influence" -> tickerBuilder.influence(value.getAsDouble());
                case "determinat" -> tickerBuilder.determinat(value.getAsDouble());
                case "from" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        tickerBuilder.from(LocalDate.parse(value.getAsString()));
                }
                case "till" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        tickerBuilder.till(LocalDate.parse(value.getAsString()));
                }
                case "tradingsession" -> tickerBuilder.tradingSession(TradingSession.getTradingSession(value.getAsInt()));
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
