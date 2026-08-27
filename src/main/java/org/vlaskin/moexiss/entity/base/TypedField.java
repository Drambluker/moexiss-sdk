package org.vlaskin.moexiss.entity.base;

/**
 * Описывает Java-тип поля ответа MOEX ISS.
 */
public interface TypedField
{
    /**
     * Возвращает Java-тип значения поля.
     *
     * @return тип значения
     */
    Class<?> getType();
}
