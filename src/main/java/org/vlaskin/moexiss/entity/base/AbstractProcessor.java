package org.vlaskin.moexiss.entity.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.InternalApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** @hidden */
@Slf4j
@InternalApi
public abstract class AbstractProcessor<T extends BasicEntity<E>, E extends Enum<?> & TypedField>
{
    private static final String INT_32 = "int32";
    private static final String INT_64 = "int64";
    private static final String DOUBLE = "double";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String DATETIME = "datetime";
    private static final String STRING = "string";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Set<UnknownAttribute> REPORTED_UNKNOWN_ATTRIBUTES = ConcurrentHashMap.newKeySet();

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
                if (shouldLogUnknownAttribute(e.getEntity(), e.getAttribute()))
                    log.warn("{} - unknown attribute: {}", e.getEntity(), e.getAttribute());
            }
            i++;
        }
    }

    static boolean shouldLogUnknownAttribute(Class<?> entity, String attribute)
    {
        return REPORTED_UNKNOWN_ATTRIBUTES.add(new UnknownAttribute(entity, attribute));
    }

    protected abstract void processValue(BasicEntity<E> entity, JsonElement value, String name, String type) throws UnknownAttributeException;

    protected void processValue(BasicEntity<E> entity, E field, JsonElement value, String type, Class<?> clazz)
    {
        if (Boolean.class.equals(clazz) && (INT_32.equals(type) || INT_64.equals(type)))
        {
            int encodedValue = value.getAsInt();
            if (encodedValue != 0 && encodedValue != 1)
                throw new IllegalArgumentException("Boolean field '" + field
                        + "' contains unsupported value " + encodedValue);
            entity.booleanFields.put(field, encodedValue == 1);
        }
        else if (Integer.class.equals(clazz) && (INT_32.equals(type) || INT_64.equals(type)))
            entity.integerFields.put(field, Math.toIntExact(value.getAsLong()));
        else if (Long.class.equals(clazz) && (INT_64.equals(type) || INT_32.equals(type)))
            entity.longFields.put(field, value.getAsLong());
        else if (Double.class.equals(clazz) && (DOUBLE.equals(type) || INT_64.equals(type) || INT_32.equals(type)))
            entity.doubleFields.put(field, value.getAsDouble());
        else if (LocalDate.class.equals(clazz) && (DATE.equals(type) || STRING.equals(type)))
        {
            if ("0000-00-00".equals(value.getAsString()))
                return;

            try
            {
                entity.localDateFields.put(field, LocalDate.parse(value.getAsString()));
            }
            catch (DateTimeParseException e)
            {
                // Некоторые колонки, возвращаемые MOEX ISS, с типом "date" могут быть на самом деле типа "datetime".
                // Например, колонка "YIELDDATE" в "marketdata_yields".
                entity.localDateFields.put(field,
                        LocalDateTime.parse(value.getAsString(), DATE_TIME_FORMATTER).toLocalDate());
            }
        }
        else if (LocalTime.class.equals(clazz) && TIME.equals(type))
            entity.localTimeFields.put(field, LocalTime.parse(value.getAsString()));
        else if (LocalDateTime.class.equals(clazz) && DATETIME.equals(type))
            entity.localDateTimeFields.put(field,
                    LocalDateTime.parse(value.getAsString(), DATE_TIME_FORMATTER));
        else if (String.class.equals(clazz) && STRING.equals(type))
            entity.stringFields.put(field, value.getAsString());
        else
            throw new IllegalArgumentException("Data type '" + type + "' of field '" + field
                    + "' is incompatible with " + clazz.getSimpleName());
    }

    @Getter
    protected static class UnknownAttributeException extends Exception
    {
        private final Class<?> entity;
        private final String attribute;

        public UnknownAttributeException(Class<?> entity, String attribute)
        {
            super(entity + " - unknown attribute: " + attribute);
            this.entity = entity;
            this.attribute = attribute;
        }
    }

    private record UnknownAttribute(Class<?> entity, String attribute)
    {
    }
}
