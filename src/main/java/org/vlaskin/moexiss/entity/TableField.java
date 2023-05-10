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
public class TableField
{
    private Integer id;
    private String name;
    private String shortTitle;
    private String title;
    private Boolean ordered;
    private Boolean system;
    private Boolean hidden;
    private Integer trendBy;
    private Boolean signed;
    private Boolean hasPercent;
    private String type;
    private Integer precision;
    private Boolean linked;

    public static class Processor extends AbstractProcessor<TableField>
    {
        @Override
        public TableField processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            TableFieldBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            TableFieldBuilder tableFieldBuilder = (TableFieldBuilder) builder;
            switch (name)
            {
                case "id" -> tableFieldBuilder.id(value.getAsInt());
                case "name" -> tableFieldBuilder.name(value.getAsString());
                case "short_title" -> tableFieldBuilder.shortTitle(value.getAsString());
                case "title" -> tableFieldBuilder.title(value.getAsString());
                case "is_ordered" -> tableFieldBuilder.ordered(value.getAsInt() == 1);
                case "is_system" -> tableFieldBuilder.system(value.getAsInt() == 1);
                case "is_hidden" -> tableFieldBuilder.hidden(value.getAsInt() == 1);
                case "trend_by" -> tableFieldBuilder.trendBy(value.getAsInt());
                case "is_signed" -> tableFieldBuilder.signed(value.getAsInt() == 1);
                case "has_percent" -> tableFieldBuilder.hasPercent(value.getAsInt() == 1);
                case "type" -> tableFieldBuilder.type(value.getAsString());
                case "precision" -> tableFieldBuilder.precision(value.getAsInt());
                case "is_linked" -> tableFieldBuilder.linked(value.getAsInt() == 1);
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
