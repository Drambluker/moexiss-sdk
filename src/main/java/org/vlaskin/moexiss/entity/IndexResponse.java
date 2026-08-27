package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.TypedField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

/**
 * Индекс с доступным периодом данных.
 */

@ToString(callSuper = true)
public class IndexResponse extends BasicEntity<IndexResponse.Fields>
{
    private IndexResponse()
    {
        super();
    }

    private IndexResponse(Map<Fields, Boolean> booleanFields,
                          Map<Fields, Integer> integerFields,
                          Map<Fields, Long> longFields,
                          Map<Fields, Double> doubleFields,
                          Map<Fields, LocalDate> localDateFields,
                          Map<Fields, LocalTime> localTimeFields,
                          Map<Fields, LocalDateTime> localDateTimeFields,
                          Map<Fields, String> stringFields)
    {
        super(booleanFields, integerFields, longFields, doubleFields, localDateFields, localTimeFields, localDateTimeFields, stringFields);
    }

    /**
     * Возвращает значение поля {@code Code}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getCode()
    {
        return stringFields.get(Fields.CODE);
    }

    /**
     * Возвращает значение поля {@code From}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public LocalDate getFrom()
    {
        return localDateFields.get(Fields.FROM);
    }

    /**
     * Возвращает значение поля {@code ShortName}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public String getShortName()
    {
        return stringFields.get(Fields.SHORTNAME);
    }

    /**
     * Возвращает значение поля {@code Till}.
     *
     * @return значение поля или {@code null}, если оно отсутствует в ответе
     */
    public LocalDate getTill()
    {
        return localDateFields.get(Fields.TILL);
    }

    /** Поля табличной секции и ожидаемые Java-типы значений. */
    @Getter
    public enum Fields implements TypedField
    {
        CODE(String.class),
        CURRENT_VALUE(Double.class),
        FROM(LocalDate.class),
        LAST_CHANGE(Double.class),
        LAST_CHANGE_PRC(Double.class),
        SECURITY_CODE(String.class),
        SHORTNAME(String.class),
        TILL(LocalDate.class);

        private final Class<?> type;

        Fields(Class<?> type)
        {
            this.type = type;
        }

        private static final Map<String, Fields> byName = Map.ofEntries(
                Map.entry("CURRENTVALUE", CURRENT_VALUE),
                Map.entry("FROM", FROM),
                Map.entry("LASTCHANGE", LAST_CHANGE),
                Map.entry("LASTCHANGEPRC", LAST_CHANGE_PRC),
                Map.entry("SECID", SECURITY_CODE),
                Map.entry("SHORTNAME", SHORTNAME),
                Map.entry("TILL", TILL),
                Map.entry("from", FROM),
                Map.entry("indexid", CODE),
                Map.entry("shortname", SHORTNAME),
                Map.entry("till", TILL)
        );
    }

    /** @hidden */
    @org.vlaskin.moexiss.InternalApi
    public static final class Processor extends AbstractProcessor<IndexResponse, Fields>
    {
        @Override
        public IndexResponse processJsonElement(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
        {
            IndexResponse entity = new IndexResponse();
            process(entity, jsonElement, columns, metadata);
            return new IndexResponse(
                    Collections.unmodifiableMap(entity.booleanFields),
                    Collections.unmodifiableMap(entity.integerFields),
                    Collections.unmodifiableMap(entity.longFields),
                    Collections.unmodifiableMap(entity.doubleFields),
                    Collections.unmodifiableMap(entity.localDateFields),
                    Collections.unmodifiableMap(entity.localTimeFields),
                    Collections.unmodifiableMap(entity.localDateTimeFields),
                    Collections.unmodifiableMap(entity.stringFields)
            );
        }

        @Override
        protected void processValue(BasicEntity<Fields> entity, JsonElement value, String name, String type) throws UnknownAttributeException
        {
            Fields field = Fields.byName.get(name);
            if (field == null)
                throw new UnknownAttributeException(getClass(), name);
            processValue(entity, field, value, type, field.getType());
        }
    }
}
