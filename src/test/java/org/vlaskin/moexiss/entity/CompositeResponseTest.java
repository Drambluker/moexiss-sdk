package org.vlaskin.moexiss.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeResponseTest
{
    @Test
    void protectsNestedListsFromModification()
    {
        List<CursorResponse> cursors = new ArrayList<>();
        IndexAnalyticsResponse response = IndexAnalyticsResponse.builder()
                .data(List.of())
                .cursors(cursors)
                .dates(List.of())
                .build();

        cursors.add(null);

        assertTrue(response.getCursors().isEmpty());
        assertThrows(UnsupportedOperationException.class,
                () -> response.getCursors().add(null));
    }

    @Test
    void usesEmptyListsForOmittedBuilderSections()
    {
        IndexAnalyticsResponse response = IndexAnalyticsResponse.builder().build();

        assertTrue(response.getData().isEmpty());
        assertTrue(response.getCursors().isEmpty());
        assertTrue(response.getDates().isEmpty());
    }
}
