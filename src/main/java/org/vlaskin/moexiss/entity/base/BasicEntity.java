package org.vlaskin.moexiss.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public abstract class BasicEntity<E extends Enum<?>>
{
    protected final Map<E, Boolean> booleanFields;
    protected final Map<E, Integer> integerFields;
    protected final Map<E, Long> longFields;
    protected final Map<E, Double> doubleFields;
    protected final Map<E, LocalDate> localDateFields;
    protected final Map<E, LocalTime> localTimeFields;
    protected final Map<E, LocalDateTime> localDateTimeFields;
    protected final Map<E, String> stringFields;

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
}
