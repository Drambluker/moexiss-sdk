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
public class IndexAnalyticsDates
{
    private final LocalDate from;
    private final LocalDate till;

    public static class Processor extends AbstractProcessor<IndexAnalyticsDates>
    {
        @Override
        public IndexAnalyticsDates processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            IndexAnalyticsDatesBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            IndexAnalyticsDatesBuilder indexAnalyticsDatesBuilder = (IndexAnalyticsDatesBuilder) builder;
            switch (name)
            {
                case "from" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        indexAnalyticsDatesBuilder.from(LocalDate.parse(value.getAsString()));
                }
                case "till" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        indexAnalyticsDatesBuilder.till(LocalDate.parse(value.getAsString()));
                }
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
