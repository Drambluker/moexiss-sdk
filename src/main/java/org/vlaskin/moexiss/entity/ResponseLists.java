package org.vlaskin.moexiss.entity;

import java.util.List;

final class ResponseLists
{
    static <T> List<T> immutableCopy(List<T> values)
    {
        return values == null ? List.of() : List.copyOf(values);
    }

    private ResponseLists()
    {
    }
}
