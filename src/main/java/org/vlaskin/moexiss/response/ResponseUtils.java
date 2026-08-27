package org.vlaskin.moexiss.response;

import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.entity.base.TypedField;

import java.util.List;

public class ResponseUtils
{
    public static <T extends BasicEntity<E>, E extends Enum<?> & TypedField> List<T> convertTo(EntityType<T, E> entityType, Response response)
    {
        return response.get(entityType);
    }

    private ResponseUtils() {}
}
