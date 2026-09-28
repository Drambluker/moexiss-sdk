package org.vlaskin.moexiss.service.history;

import java.util.List;

/**
 * Неизменяемая страница после проверки курсора ISS.
 * @param <T> тип строки
 * @param rows строки текущей страницы
 * @param start начальное смещение
 * @param total число строк запроса
 * @param pageSize предельный размер страницы источника
 * @param complete получена последняя страница; не означает полноту событий или торговых дат
 */
public record HistoryPage<T>(List<T> rows, int start, int total, int pageSize, boolean complete)
{
    /** Создаёт защитную копию строк. */
    public HistoryPage
    {
        rows = List.copyOf(rows);
        int next = Math.addExact(start, rows.size());
        if (start < 0 || total < 0 || pageSize <= 0 || rows.size() > pageSize
                || next > total || start > total || rows.isEmpty() && start < total
                || complete != (next == total))
            throw new IllegalArgumentException("History page cursor is inconsistent");
    }

    /** @return следующее смещение, рассчитанное по фактически полученным строкам */
    public int nextOffset()
    {
        return Math.addExact(start, rows.size());
    }
}
