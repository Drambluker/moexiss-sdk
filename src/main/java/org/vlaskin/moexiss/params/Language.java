package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language
{
    RU("ru"), EN("en");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
