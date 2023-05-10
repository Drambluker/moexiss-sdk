package org.vlaskin.moexiss.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseDeserializer;
import org.vlaskin.moexiss.response.field.FieldResponse;
import org.vlaskin.moexiss.response.field.FieldResponseDeserializer;

public abstract class BaseService
{
    protected static final String BASE_URL = "https://iss.moex.com";

    protected final Gson gson;

    public BaseService()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Response.class, new ResponseDeserializer());
        gsonBuilder.registerTypeAdapter(FieldResponse.class, new FieldResponseDeserializer());
        gson = gsonBuilder.create();
    }

    protected static void pasteBasicRequestParams(StringBuilder requestBuilder, String... only)
    {
        requestBuilder.append("?iss.meta=on");
        if (only != null)
            requestBuilder.append("&iss.only=").append(String.join(",", only));
    }
}
