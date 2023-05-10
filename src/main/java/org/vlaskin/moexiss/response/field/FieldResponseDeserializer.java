package org.vlaskin.moexiss.response.field;

import org.vlaskin.moexiss.response.AbstractResponseDeserializer;

public class FieldResponseDeserializer extends AbstractResponseDeserializer<FieldResponse>
{
    @Override
    protected Class<FieldResponse> getResponseClass()
    {
        return FieldResponse.class;
    }
}
