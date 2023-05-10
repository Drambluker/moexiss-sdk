package org.vlaskin.moexiss.response;

public class ResponseDeserializer extends AbstractResponseDeserializer<Response>
{
    @Override
    protected Class<Response> getResponseClass()
    {
        return Response.class;
    }
}
