package org.vlaskin.moexiss.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.Validate;
import org.vlaskin.moexiss.http.ApacheMoexHttpTransport;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseDeserializer;
import org.vlaskin.moexiss.response.field.FieldResponse;
import org.vlaskin.moexiss.response.field.FieldResponseDeserializer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class BaseService
{
    public static final String DEFAULT_BASE_URL = "https://iss.moex.com";

    @Deprecated
    protected static final String BASE_URL = DEFAULT_BASE_URL;

    protected final String baseUrl;
    protected final MoexHttpTransport httpTransport;
    protected final Gson gson;

    public BaseService()
    {
        this(DEFAULT_BASE_URL, new ApacheMoexHttpTransport());
    }

    protected BaseService(String baseUrl, MoexHttpTransport httpTransport)
    {
        this.baseUrl = removeTrailingSlash(Validate.notBlank(baseUrl, "Base URL must not be blank").strip());
        Validate.notBlank(this.baseUrl, "Base URL must not contain only slashes");
        this.httpTransport = Validate.notNull(httpTransport, "HTTP transport must not be null");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Response.class, new ResponseDeserializer());
        gsonBuilder.registerTypeAdapter(FieldResponse.class, new FieldResponseDeserializer());
        gson = gsonBuilder.create();
    }

    protected String get(CharSequence request) throws IOException
    {
        return httpTransport.get(request.toString());
    }

    protected static void pasteBasicRequestParams(StringBuilder requestBuilder, String... only)
    {
        requestBuilder.append("?iss.meta=on");
        if (only != null && only.length > 0)
            requestBuilder.append("&iss.only=").append(String.join(",", only));
    }

    protected static String encodeQueryParameter(Object value)
    {
        return URLEncoder.encode(value.toString(), StandardCharsets.UTF_8);
    }

    private static String removeTrailingSlash(String url)
    {
        int end = url.length();
        while (end > 0 && url.charAt(end - 1) == '/')
            end--;
        return url.substring(0, end);
    }
}
