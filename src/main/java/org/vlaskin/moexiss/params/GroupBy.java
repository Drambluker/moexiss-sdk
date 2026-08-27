package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

/** Способ группировки результатов поиска ценных бумаг. */
@AllArgsConstructor
public enum GroupBy
{
    /** По группе ценных бумаг. */
    GROUP("group"),
    /** По типу ценной бумаги. */
    TYPE("type");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
