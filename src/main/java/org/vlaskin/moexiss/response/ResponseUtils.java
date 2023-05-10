package org.vlaskin.moexiss.response;

import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.util.List;

public class ResponseUtils
{
    @SuppressWarnings("unchecked")
    public static <T extends BasicEntity<E>, E extends Enum<?>> List<T> convertTo(EntityType<T, E> entityType, Response response)
    {
        return response.getData().get(entityType).stream()
                .map(entity -> (T) entity)
                .toList();
    }

    private ResponseUtils() {}
}
