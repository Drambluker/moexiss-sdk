package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;

@Getter
@Builder
@ToString
public class MarketDataYield
{
    private String boardId;
    private String securityId;

    public static class Processor extends AbstractProcessor<MarketDataYield>
    {
        @Override
        public MarketDataYield processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            MarketDataYieldBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            MarketDataYieldBuilder marketDataYieldBuilder = (MarketDataYieldBuilder) builder;
            switch (name)
            {
                case "boardid" -> marketDataYieldBuilder.boardId(value.getAsString());
                case "secid" -> marketDataYieldBuilder.securityId(value.getAsString());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
