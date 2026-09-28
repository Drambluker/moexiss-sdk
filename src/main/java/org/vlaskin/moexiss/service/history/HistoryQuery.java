package org.vlaskin.moexiss.service.history;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Параметры одной страницы исторических итогов торгов.
 * @param engine торговая система
 * @param market рынок
 * @param board режим торгов; null допустим только для индекса
 * @param instrument код инструмента
 * @param from первая дата включительно
 * @param to последняя дата включительно
 * @param start смещение строки, не номер страницы
 */
public record HistoryQuery(String engine, String market, String board, String instrument,
                           LocalDate from, LocalDate to, int start)
{
    /** Проверяет обязательные значения и границы периода. */
    public HistoryQuery
    {
        Objects.requireNonNull(from, "Start date is required");
        Objects.requireNonNull(to, "End date is required");
        if (engine == null || engine.isBlank() || market == null || market.isBlank()
                || instrument == null || instrument.isBlank() || start < 0 || from.isAfter(to))
            throw new IllegalArgumentException("Invalid history query");
    }
}
