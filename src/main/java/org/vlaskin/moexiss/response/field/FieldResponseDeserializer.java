package org.vlaskin.moexiss.response.field;

import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.response.AbstractResponseDeserializer;

import java.util.List;
import java.util.Map;

public class FieldResponseDeserializer extends AbstractResponseDeserializer<FieldResponse>
{
    @Override
    protected Class<FieldResponse> getResponseClass()
    {
        return FieldResponse.class;
    }

    @Override
    protected FieldResponse createResponse(
            Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data)
    {
        return new FieldResponse(data);
    }
}
