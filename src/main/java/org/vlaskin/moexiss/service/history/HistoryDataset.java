package org.vlaskin.moexiss.service.history;

import java.util.List;

/**
 * Данные и отдельно подтверждение их полноты.
 * @param <T> тип события
 * @param availability доступность данного API
 * @param rows найденные события
 * @param complete подтверждена полнота охвата; пустой список без этого признака ничего не доказывает
 * @param reason причина ограничений на английском языке
 */
public record HistoryDataset<T>(HistoryAvailability availability, List<T> rows, boolean complete, String reason)
{
    /** Защищает список событий от внешнего изменения. */
    public HistoryDataset
    {
        rows = List.copyOf(rows);
        java.util.Objects.requireNonNull(availability);
        if (complete && availability != HistoryAvailability.SUPPORTED)
            throw new IllegalArgumentException("Unavailable history cannot be complete");
    }
}
