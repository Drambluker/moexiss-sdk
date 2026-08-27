package org.vlaskin.moexiss.entity.base;

import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Базовая модель строки таблицы MOEX ISS с типизированным доступом к динамическим полям.
 *
 * @param <E> перечисление полей конкретной таблицы
 */
@ToString
public abstract class BasicEntity<E extends Enum<?> & TypedField>
{
    protected final Map<E, Boolean> booleanFields;
    protected final Map<E, Integer> integerFields;
    protected final Map<E, Long> longFields;
    protected final Map<E, Double> doubleFields;
    protected final Map<E, LocalDate> localDateFields;
    protected final Map<E, LocalTime> localTimeFields;
    protected final Map<E, LocalDateTime> localDateTimeFields;
    protected final Map<E, String> stringFields;

    protected BasicEntity(Map<E, Boolean> booleanFields,
                          Map<E, Integer> integerFields,
                          Map<E, Long> longFields,
                          Map<E, Double> doubleFields,
                          Map<E, LocalDate> localDateFields,
                          Map<E, LocalTime> localTimeFields,
                          Map<E, LocalDateTime> localDateTimeFields,
                          Map<E, String> stringFields)
    {
        this.booleanFields = Map.copyOf(booleanFields);
        this.integerFields = Map.copyOf(integerFields);
        this.longFields = Map.copyOf(longFields);
        this.doubleFields = Map.copyOf(doubleFields);
        this.localDateFields = Map.copyOf(localDateFields);
        this.localTimeFields = Map.copyOf(localTimeFields);
        this.localDateTimeFields = Map.copyOf(localDateTimeFields);
        this.stringFields = Map.copyOf(stringFields);
    }

    protected BasicEntity()
    {
        booleanFields = new HashMap<>();
        integerFields = new HashMap<>();
        longFields = new HashMap<>();
        doubleFields = new HashMap<>();
        localDateFields = new HashMap<>();
        localTimeFields = new HashMap<>();
        localDateTimeFields = new HashMap<>();
        stringFields = new HashMap<>();
    }

    /**
     * Возвращает значение поля и проверяет запрошенный тип, даже если значение отсутствует.
     *
     * @param field поле модели
     * @param type ожидаемый Java-тип поля
     * @param <T> тип возвращаемого значения
     * @return значение или {@code null}, если поле отсутствует в ответе
     * @throws NullPointerException если поле или тип не заданы
     * @throws IllegalArgumentException если ожидаемый тип не совпадает с типом поля
     */
    public final <T> T get(E field, Class<T> type)
    {
        Objects.requireNonNull(field, "Field must not be null");
        Objects.requireNonNull(type, "Field type must not be null");
        if (!field.getType().equals(type))
            throw new IllegalArgumentException(
                    "Field " + field + " has type " + field.getType().getSimpleName()
                            + ", but " + type.getSimpleName() + " was requested");
        return type.cast(getFields(type).get(field));
    }

    /**
     * Проверяет, присутствует ли непустое значение поля в ответе.
     *
     * @param field поле модели
     * @return {@code true}, если ответ содержит непустое значение
     * @throws NullPointerException если поле не задано
     */
    public final boolean has(E field)
    {
        Objects.requireNonNull(field, "Field must not be null");
        return getFields(field.getType()).containsKey(field);
    }

    private Map<E, ?> getFields(Class<?> type)
    {
        if (Boolean.class.equals(type))
            return booleanFields;
        if (Integer.class.equals(type))
            return integerFields;
        if (Long.class.equals(type))
            return longFields;
        if (Double.class.equals(type))
            return doubleFields;
        if (LocalDate.class.equals(type))
            return localDateFields;
        if (LocalTime.class.equals(type))
            return localTimeFields;
        if (LocalDateTime.class.equals(type))
            return localDateTimeFields;
        if (String.class.equals(type))
            return stringFields;
        throw new IllegalArgumentException("Unsupported field type: " + type.getName());
    }
}
