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
public class IndexAnalyticsData
{
    private final String indexId;
    private final LocalDate tradeDate;
    private final String ticker;
    private final String shortname;
    private final String secId;
    private final Double weight;
    private final TradingSession tradingSession;

    public static class Processor extends AbstractProcessor<IndexAnalyticsData>
    {
        @Override
        public IndexAnalyticsData processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            IndexAnalyticsDataBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            IndexAnalyticsDataBuilder indexAnalyticsDataBuilder = (IndexAnalyticsDataBuilder) builder;
            switch (name)
            {
                case "indexid" -> indexAnalyticsDataBuilder.indexId(value.getAsString());
                case "tradedate" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        indexAnalyticsDataBuilder.tradeDate(LocalDate.parse(value.getAsString()));
                }
                case "ticker" -> indexAnalyticsDataBuilder.ticker(value.getAsString());
                case "shortnames" -> indexAnalyticsDataBuilder.shortname(value.getAsString());
                case "secids" -> indexAnalyticsDataBuilder.secId(value.getAsString());
                case "weight" -> indexAnalyticsDataBuilder.weight(value.getAsDouble());
                case "tradingsession" -> indexAnalyticsDataBuilder.tradingSession(TradingSession.getTradingSession(value.getAsInt()));
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
