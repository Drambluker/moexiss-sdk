package org.vlaskin.moexiss.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractResponseDeserializer<T extends Response> implements JsonDeserializer<T>
{
    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        if (!jsonElement.isJsonObject())
            throw new JsonParseException("MOEX ISS response root must be an object");

        Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> dataMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().asMap().entrySet())
        {
            EntityType<? extends BasicEntity<?>, ? extends Enum<?>> entityType = EntityType.getEntityType(getResponseClass(), entry.getKey());
            if (entityType == null)
                continue;

            String sectionName = entry.getKey();
            JsonObject section = requireObject(entry.getValue(), "Section '" + sectionName + "'");
            JsonObject metadata = requireObject(section.get("metadata"),
                    "Field 'metadata' of section '" + sectionName + "'");
            JsonArray columns = requireArray(section.get("columns"),
                    "Field 'columns' of section '" + sectionName + "'");
            JsonArray data = requireArray(section.get("data"),
                    "Field 'data' of section '" + sectionName + "'");
            validateColumns(sectionName, columns, metadata);

            List<BasicEntity<?>> dataList = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < data.size(); rowIndex++)
            {
                JsonElement element = data.get(rowIndex);
                JsonArray row = requireArray(element,
                        "Row " + (rowIndex + 1) + " of section '" + sectionName + "'");
                if (row.size() != columns.size())
                    throw new JsonParseException("Row " + (rowIndex + 1) + " of section '" + sectionName
                            + "' contains " + row.size() + " values for " + columns.size() + " columns");

                try
                {
                    dataList.add(entityType.createInstance(row, columns, metadata));
                }
                catch (RuntimeException e)
                {
                    throw new JsonParseException("Failed to parse row " + (rowIndex + 1)
                            + " of section '" + sectionName + "': " + e.getMessage(), e);
                }
            }
            dataMap.put(entityType, dataList);
        }
        return createResponse(dataMap);
    }

    private static JsonObject requireObject(JsonElement element, String description)
    {
        if (element == null || !element.isJsonObject())
            throw new JsonParseException(description + " must be an object");
        return element.getAsJsonObject();
    }

    private static JsonArray requireArray(JsonElement element, String description)
    {
        if (element == null || !element.isJsonArray())
            throw new JsonParseException(description + " must be an array");
        return element.getAsJsonArray();
    }

    private static void validateColumns(String sectionName, JsonArray columns, JsonObject metadata)
    {
        for (JsonElement column : columns)
        {
            if (!column.isJsonPrimitive() || !column.getAsJsonPrimitive().isString())
                throw new JsonParseException("Column name of section '" + sectionName + "' must be a string");

            String columnName = column.getAsString();
            JsonElement fieldMetadata = metadata.get(columnName);
            if (fieldMetadata == null || !fieldMetadata.isJsonObject())
                throw new JsonParseException("Metadata for column '" + columnName
                        + "' is missing in section '" + sectionName + "'");

            JsonElement fieldType = fieldMetadata.getAsJsonObject().get("type");
            if (fieldType == null || !fieldType.isJsonPrimitive() || !fieldType.getAsJsonPrimitive().isString())
                throw new JsonParseException("String type is missing in metadata for column '" + columnName
                        + "' of section '" + sectionName + "'");
        }
    }

    protected abstract Class<T> getResponseClass();

    protected abstract T createResponse(
            Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data);
}
