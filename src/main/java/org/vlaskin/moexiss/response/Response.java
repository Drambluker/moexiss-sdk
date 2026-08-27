package org.vlaskin.moexiss.response;

import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.entity.base.TypedField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response
{
    private final Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data;

    public Response(Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data)
    {
        Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> copy =
                new HashMap<>();
        data.forEach((type, entities) -> copy.put(type, List.copyOf(entities)));
        this.data = Map.copyOf(copy);
    }

    @SuppressWarnings("unchecked")
    final <T extends BasicEntity<E>, E extends Enum<?> & TypedField> List<T> get(EntityType<T, E> entityType)
    {
        return (List<T>) data.getOrDefault(entityType, List.of());
    }
}
