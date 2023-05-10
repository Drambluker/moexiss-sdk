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
public class Engine
{
    private Integer id;
    private String name;
    private String title;
    private String shortTitle;

    public static class Processor extends AbstractProcessor<Engine>
    {
        @Override
        public Engine processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            EngineBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            EngineBuilder engineBuilder = (EngineBuilder) builder;
            switch (name)
            {
                case "id" -> engineBuilder.id(value.getAsInt());
                case "name", "NAME" -> engineBuilder.name(value.getAsString());
                case "title" -> engineBuilder.title(value.getAsString());
                case "short_title" -> engineBuilder.shortTitle(value.getAsString());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
