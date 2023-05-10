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
public class Market
{
    private Integer id;
    private String name;
    private String title;

    public static class Processor extends AbstractProcessor<Market>
    {
        @Override
        public Market processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            MarketBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            MarketBuilder marketBuilder = (MarketBuilder) builder;
            switch (name)
            {
                case "id" -> marketBuilder.id(value.getAsInt());
                case "NAME" -> marketBuilder.name(value.getAsString());
                case "title" -> marketBuilder.title(value.getAsString());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
