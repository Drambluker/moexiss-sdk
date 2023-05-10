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
public class Cursor
{
    private final Long index;
    private final Long total;
    private final Long pageSize;
    private final LocalDate prevDate;
    private final LocalDate nextDate;

    public static class Processor extends AbstractProcessor<Cursor>
    {
        @Override
        public Cursor processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            CursorBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            CursorBuilder cursorBuilder = (CursorBuilder) builder;
            switch (name)
            {
                case "INDEX" -> cursorBuilder.index(value.getAsLong());
                case "TOTAL" -> cursorBuilder.total(value.getAsLong());
                case "PAGESIZE" -> cursorBuilder.pageSize(value.getAsLong());
                case "PREV_DATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        cursorBuilder.prevDate(LocalDate.parse(value.getAsString()));
                }
                case "NEXT_DATE" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        cursorBuilder.nextDate(LocalDate.parse(value.getAsString()));
                }
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
