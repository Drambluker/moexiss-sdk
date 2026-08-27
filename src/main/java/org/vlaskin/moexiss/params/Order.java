package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

/** Направление сортировки. */
@AllArgsConstructor
public enum Order
{
    /** По возрастанию. */
    ASC("asc"),
    /** По убыванию. */
    DESC("desc");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
