package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GroupBy
{
    GROUP("group"), TYPE("type");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
