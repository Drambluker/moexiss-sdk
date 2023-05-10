package org.vlaskin.moexiss.entity.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Slf4j
public abstract class AbstractProcessor<T extends BasicEntity<E>, E extends Enum<?>>
{
    private static final String INT_32 = "int32";
    private static final String INT_64 = "int64";
    private static final String DOUBLE = "double";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String DATETIME = "datetime";
    private static final String STRING = "string";

    public abstract T processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata);

    protected void process(BasicEntity<E> entity, JsonElement jsonElement, JsonArray columns, JsonObject metadata)
    {
        int i = 0;
        for (JsonElement column : columns)
        {
            JsonElement value = jsonElement.getAsJsonArray().get(i);
            if (value.isJsonNull())
            {
                i++;
                continue;
            }

            String columnName = column.getAsString();
            String dataType = metadata.getAsJsonObject(columnName).get("type").getAsString();

            try
            {
                processValue(entity, value, columnName, dataType);
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

    protected abstract void processValue(BasicEntity<E> entity, JsonElement value, String name, String type) throws UnknownAttributeException;

    protected void processValue(BasicEntity<E> entity, E field, JsonElement value, String type, Class<?> clazz)
    {
        if (Boolean.class.equals(clazz) && (INT_32.equals(type) || INT_64.equals(type)))
            entity.getBooleanFields().put(field, value.getAsInt() == 1);
        else if (Integer.class.equals(clazz) && INT_32.equals(type))
            entity.getIntegerFields().put(field, value.getAsInt());
        else if (Long.class.equals(clazz) && (INT_64.equals(type) || INT_32.equals(type)))
            entity.getLongFields().put(field, value.getAsLong());
        else if (Double.class.equals(clazz) && (DOUBLE.equals(type) || INT_64.equals(type)))
            entity.getDoubleFields().put(field, value.getAsDouble());
        else if (LocalDate.class.equals(clazz) && (DATE.equals(type) || STRING.equals(type)))
        {
            if ("0000-00-00".equals(value.getAsString()))
                return;

            try
            {
                entity.getLocalDateFields().put(field, LocalDate.parse(value.getAsString()));
            }
            catch (DateTimeParseException e)
            {
                // Некоторые колонки, возвращаемые MOEX ISS, с типом "date" могут быть на самом деле типа "datetime".
                // Например, колонка "YIELDDATE" в "marketdata_yields".
                entity.getLocalDateTimeFields().put(field, LocalDateTime.parse(value.getAsString(), DateUtils.DATE_TIME_FORMATTER));
            }
        }
        else if (LocalTime.class.equals(clazz) && TIME.equals(type))
            entity.getLocalTimeFields().put(field, LocalTime.parse(value.getAsString()));
        else if (LocalDateTime.class.equals(clazz) && DATETIME.equals(type))
            entity.getLocalDateTimeFields().put(field, LocalDateTime.parse(value.getAsString(), DateUtils.DATE_TIME_FORMATTER));
        else if (String.class.equals(clazz) && STRING.equals(type))
            entity.getStringFields().put(field, value.getAsString());
        else
            log.warn("Data type \"{}\" does not match class", type);
    }

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
