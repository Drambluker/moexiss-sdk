package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Order
{
    ASC("asc"), DESC("desc");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
