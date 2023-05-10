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
public class DataVersion
{
    private Integer version;
    private Long sequenceNumber;

    public static class Processor extends AbstractProcessor<DataVersion>
    {
        @Override
        public DataVersion processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            DataVersionBuilder builder = builder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            DataVersionBuilder dataVersionBuilder = (DataVersionBuilder) builder;
            switch (name)
            {
                case "data_version" -> dataVersionBuilder.version(value.getAsInt());
                case "seqnum" -> dataVersionBuilder.sequenceNumber(value.getAsLong());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
