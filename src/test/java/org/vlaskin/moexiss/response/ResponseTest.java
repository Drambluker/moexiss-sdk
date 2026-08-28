package org.vlaskin.moexiss.response;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseTest
{
    @Test
    void protectsSectionsFromModification()
    {
        List<SecurityResponse> securities = new ArrayList<>();
        Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>,
                List<? extends BasicEntity<?>>> data = new HashMap<>();
        data.put(EntityType.SECURITY, securities);
        Response response = new Response(data);

        securities.add(null);
        data.clear();

        List<SecurityResponse> result = ResponseUtils.convertTo(EntityType.SECURITY, response);
        assertTrue(result.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> result.add(null));
    }

    @Test
    void returnsEmptyListForMissingSection()
    {
        Response response = new Response(Map.of());

        List<SecurityResponse> result = ResponseUtils.convertTo(EntityType.SECURITY, response);

        assertTrue(result.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> result.add(null));
    }
}
