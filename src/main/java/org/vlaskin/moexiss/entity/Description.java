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
public class Description
{
    private final String name;
    private final String title;
    private final String value;
    private final String type;
    private final Long sortOrder;
    private final Boolean hidden;
    private final Long precision;

    public static class Processor extends AbstractProcessor<Description>
    {
        @Override
        public Description processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            DescriptionBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            DescriptionBuilder descriptionBuilder = (DescriptionBuilder) builder;
            switch (name)
            {
                case "name" -> descriptionBuilder.name(value.getAsString());
                case "title" -> descriptionBuilder.title(value.getAsString());
                case "value" -> descriptionBuilder.value(value.getAsString());
                case "type" -> descriptionBuilder.type(value.getAsString());
                case "sort_order" -> descriptionBuilder.sortOrder(value.getAsLong());
                case "is_hidden" -> descriptionBuilder.hidden(value.getAsLong() == 1L);
                case "precision" -> descriptionBuilder.precision(value.getAsLong());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
