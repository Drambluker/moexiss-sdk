package org.vlaskin.moexiss.entity.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractProcessorTest
{
    @Test
    void reportsEachUnknownAttributeOnlyOnce()
    {
        assertTrue(AbstractProcessor.shouldLogUnknownAttribute(AbstractProcessorTest.class, "future_column"));
        assertFalse(AbstractProcessor.shouldLogUnknownAttribute(AbstractProcessorTest.class, "future_column"));
        assertTrue(AbstractProcessor.shouldLogUnknownAttribute(AbstractProcessorTest.class, "another_column"));
    }
}
