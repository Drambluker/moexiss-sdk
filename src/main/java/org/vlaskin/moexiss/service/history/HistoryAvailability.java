package org.vlaskin.moexiss.service.history;

/** Доступность источника; успешный HTTP-ответ сам по себе не подтверждает полноту данных. */
public enum HistoryAvailability
{
    SUPPORTED, UNSUPPORTED, INCOMPLETE
}
