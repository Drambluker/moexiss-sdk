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
public class Index
{
    private final String indexId;
    private final String secId;
    private final String shortname;
    private final LocalDate from;
    private final LocalDate till;
    private final Double currentValue;
    private final Double lastChangePrc;
    private final Double lastChange;

    public static class Processor extends AbstractProcessor<Index>
    {
        @Override
        public Index processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            IndexBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            IndexBuilder indexBuilder = (IndexBuilder) builder;
            switch (name)
            {
                case "indexid" -> indexBuilder.indexId(value.getAsString());
                case "SECID" -> indexBuilder.secId(value.getAsString());
                case "shortname", "SHORTNAME" -> indexBuilder.shortname(value.getAsString());
                case "from", "FROM" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        indexBuilder.from(LocalDate.parse(value.getAsString()));
                }
                case "till", "TILL" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        indexBuilder.till(LocalDate.parse(value.getAsString()));
                }
                case "CURRENTVALUE" -> indexBuilder.currentValue(value.getAsDouble());
                case "LASTCHANGEPRC" -> indexBuilder.lastChangePrc(value.getAsDouble());
                case "LASTCHANGE" -> indexBuilder.lastChange(value.getAsDouble());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
