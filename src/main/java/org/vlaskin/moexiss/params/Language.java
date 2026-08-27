package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;

/** Язык текстовых полей ответа MOEX ISS. */
@AllArgsConstructor
public enum Language
{
    /** Русский язык. */
    RU("ru"),
    /** Английский язык. */
    EN("en");

    private final String code;

    @Override
    public String toString()
    {
        return code;
    }
}
