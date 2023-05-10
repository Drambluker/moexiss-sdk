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
public class BoardGroup
{
    private Integer id;
    private String slug;
    private String title;
    private Boolean defaultGroup;
    private Boolean traded;

    public static class Processor extends AbstractProcessor<BoardGroup>
    {
        @Override
        public BoardGroup processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            BoardGroupBuilder builder = new BoardGroupBuilder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            BoardGroupBuilder boardGroupBuilder = (BoardGroupBuilder) builder;
            switch (name)
            {
                case "id" -> boardGroupBuilder.id(value.getAsInt());
                case "slug" -> boardGroupBuilder.slug(value.getAsString());
                case "title" -> boardGroupBuilder.title(value.getAsString());
                case "is_default" -> boardGroupBuilder.defaultGroup(value.getAsInt() == 1);
                case "is_traded" -> boardGroupBuilder.traded(value.getAsInt() == 1);
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
