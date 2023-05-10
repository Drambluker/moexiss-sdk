package org.vlaskin.moexiss.entity.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProcessor<T>
{
    public abstract T processJsonElement(JsonElement jsonElement, JsonArray columns);

    protected void process(Object builder, JsonElement jsonElement, JsonArray columns)
    {
        int i = 0;
        for (JsonElement element : columns)
        {
            JsonElement value = jsonElement.getAsJsonArray().get(i);
            if (value.isJsonNull())
            {
                i++;
                continue;
            }

            try
            {
                processValue(builder, value, element.getAsString());
            }
            catch (UnknownAttributeException e)
            {
                log.warn("""
                        {}
                        Columns: {}
                        Data: {}
                        """, e.getMessage(), columns, jsonElement);
            }
            i++;
        }
    }

    protected abstract void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException;

    @Getter
    protected static class UnknownAttributeException extends Exception
    {
        private final Class<?> entity;
        private final String attribute;

        public UnknownAttributeException(Class<?> entity, String attribute)
        {
            super(entity + " - Unknown attribute: " + attribute);
            this.entity = entity;
            this.attribute = attribute;
        }
    }
}
