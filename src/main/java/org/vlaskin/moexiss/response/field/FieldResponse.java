package org.vlaskin.moexiss.response.field;

import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.response.Response;

import java.util.List;
import java.util.Map;

public class FieldResponse extends Response
{
    public FieldResponse(Map<EntityType<?>, List<?>> data)
    {
        super(data);
    }
}
