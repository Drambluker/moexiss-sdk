package org.vlaskin.moexiss.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.vlaskin.moexiss.InternalApi;
import org.vlaskin.moexiss.http.ApacheMoexHttpTransport;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseDeserializer;
import org.vlaskin.moexiss.response.field.FieldResponse;
import org.vlaskin.moexiss.response.field.FieldResponseDeserializer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый класс сервисов MOEX ISS, отвечающий за запросы, кодирование и разбор JSON.
 *
 * @hidden
 */
@Slf4j
@InternalApi
public abstract class BaseService
{
    /** Официальный базовый адрес MOEX ISS. */
    public static final String DEFAULT_BASE_URL = "https://iss.moex.com";

    protected final String baseUrl;
    protected final MoexHttpTransport httpTransport;
    protected final Gson gson;

    /** Создаёт сервис для официального адреса MOEX ISS. */
    protected BaseService()
    {
        this(DEFAULT_BASE_URL, new ApacheMoexHttpTransport());
    }

    protected BaseService(String baseUrl, MoexHttpTransport httpTransport)
    {
        this.baseUrl = removeTrailingSlash(
                Validate.notBlank(baseUrl, "Base URL must not be blank").strip());
        Validate.notBlank(this.baseUrl,
                "Base URL must not contain only '/' characters");
        this.httpTransport = Validate.notNull(httpTransport,
                "HTTP transport must not be null");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Response.class, new ResponseDeserializer());
        gsonBuilder.registerTypeAdapter(FieldResponse.class, new FieldResponseDeserializer());
        gson = gsonBuilder.create();
    }

    protected String get(CharSequence request) throws IOException
    {
        return httpTransport.get(request.toString());
    }

    protected <T> T getAndParseResponse(CharSequence request, Class<T> responseType)
            throws IOException
    {
        String response = get(request);
        log.debug("Response received: {} characters", response == null ? 0 : response.length());
        return parseResponse(response, responseType);
    }

    protected <T> T parseResponse(String response, Class<T> responseType) throws IOException
    {
        if (response == null || response.isBlank())
            throw new IOException("MOEX ISS returned an empty response");
        try
        {
            T result = gson.fromJson(response, responseType);
            if (result == null)
                throw new IOException("MOEX ISS returned an empty response");
            return result;
        }
        catch (JsonParseException exception)
        {
            throw new IOException("Failed to parse MOEX ISS response", exception);
        }
    }

    protected static void pasteBasicRequestParams(StringBuilder requestBuilder, String... only)
    {
        requestBuilder.append("?iss.meta=on");
        if (only != null && only.length > 0)
            appendQueryParameter(requestBuilder, "iss.only", List.of(only));
    }

    protected static String encodeQueryParameter(Object value)
    {
        return URLEncoder.encode(value.toString(), StandardCharsets.UTF_8);
    }

    protected static String encodePathSegment(Object value)
    {
        return encodeQueryParameter(value).replace("+", "%20");
    }

    protected static void appendQueryParameter(StringBuilder requestBuilder, String name, Object value)
    {
        requestBuilder.append('&').append(name).append('=').append(encodeQueryParameter(value));
    }

    protected static void appendQueryParameter(StringBuilder requestBuilder, String name,
                                               Collection<?> values)
    {
        String encodedValues = values.stream()
                .map(BaseService::encodeQueryParameter)
                .collect(Collectors.joining(","));
        requestBuilder.append('&').append(name).append('=').append(encodedValues);
    }

    protected static <T> T requireParams(T params)
    {
        return Validate.notNull(params, "Request parameters must not be null");
    }

    protected static <T> T requireParameter(T value, String name)
    {
        return Validate.notNull(value, "Parameter " + name + " must not be null");
    }

    protected static String requireTextParameter(String value, String name)
    {
        return Validate.notBlank(value, "Parameter " + name + " must not be blank");
    }

    protected static void requireNonNegative(int value, String name)
    {
        Validate.isTrue(value >= 0, "Parameter " + name + " must not be negative");
    }

    protected static void requirePositive(int value, String name)
    {
        Validate.isTrue(value > 0, "Parameter " + name + " must be greater than zero");
    }

    protected static int calculateStartIndex(int pageIndex, int limit)
    {
        requireNonNegative(pageIndex, "pageIndex");
        requirePositive(limit, "limit");
        try
        {
            return Math.multiplyExact(pageIndex, limit);
        }
        catch (ArithmeticException exception)
        {
            throw new IllegalArgumentException(
                    "Product of pageIndex and limit exceeds the supported range",
                    exception);
        }
    }

    private static String removeTrailingSlash(String url)
    {
        int end = url.length();
        while (end > 0 && url.charAt(end - 1) == '/')
            end--;
        return url.substring(0, end);
    }
}
