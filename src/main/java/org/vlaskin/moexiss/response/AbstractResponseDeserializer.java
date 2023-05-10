package org.vlaskin.moexiss.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import lombok.SneakyThrows;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractResponseDeserializer<T extends Response> implements JsonDeserializer<T>
{
    @Override
    @SneakyThrows
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> dataMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().asMap().entrySet())
        {
            JsonObject metadata = entry.getValue().getAsJsonObject().getAsJsonObject("metadata");
            JsonArray columns = entry.getValue().getAsJsonObject().getAsJsonArray("columns");
            JsonArray data = entry.getValue().getAsJsonObject().getAsJsonArray("data");

            EntityType<? extends BasicEntity<?>, ? extends Enum<?>> entityType = EntityType.getEntityType(getResponseClass(), entry.getKey());
            if (entityType == null)
                continue;

            List<BasicEntity<?>> dataList = new ArrayList<>();
            for (JsonElement element : data)
            {
                BasicEntity<?> entity = entityType.createInstance(element, columns, metadata);
                dataList.add(entity);
            }
            dataMap.put(entityType, Collections.unmodifiableList(dataList));
        }
        return getResponseClass().getConstructor(Map.class).newInstance(Collections.unmodifiableMap(dataMap));
    }

    protected abstract Class<T> getResponseClass();
}
