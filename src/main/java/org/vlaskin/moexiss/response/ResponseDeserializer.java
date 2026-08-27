package org.vlaskin.moexiss.response;

import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.util.List;
import java.util.Map;

public class ResponseDeserializer extends AbstractResponseDeserializer<Response>
{
    @Override
    protected Class<Response> getResponseClass()
    {
        return Response.class;
    }

    @Override
    protected Response createResponse(
            Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data)
    {
        return new Response(data);
    }
}
