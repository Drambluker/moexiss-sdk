package org.vlaskin.moexiss.response;

import org.vlaskin.moexiss.entity.base.EntityType;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtils
{
    @SuppressWarnings("unchecked")
    public static <T> List<T> convertTo(EntityType<T> entityType, Response response)
    {
        return response.getData().get(entityType).stream()
                .map(entity -> (T) entity)
                .collect(Collectors.toList());
    }
}
