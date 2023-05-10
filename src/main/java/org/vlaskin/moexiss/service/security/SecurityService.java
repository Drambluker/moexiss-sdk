package org.vlaskin.moexiss.service.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.fluent.Request;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.DescriptionResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.SecurityInfoResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.security.params.IndicesSecurityParams;
import org.vlaskin.moexiss.service.security.params.InfoSecurityParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class SecurityService extends BaseService
{
    // BEGIN --> https://iss.moex.com/iss/reference/5

    /**
     * Список бумаг торгуемых на московской бирже
     */
    public List<SecurityResponse> getList(ListSecurityParams params) throws IOException
    {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/securities.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage())
                .append("&start=").append(params.getPageIndex() * params.getLimit())
                .append("&limit=").append(params.getLimit());

        if (params.getQuery() != null)
            requestBuilder.append("&q=").append(params.getQuery());
        if (params.getEngine() != null)
            requestBuilder.append("&engine=").append(params.getEngine());
        if (params.getMarket() != null)
            requestBuilder.append("&market=").append(params.getMarket());
        if (params.getTrading() != null)
            requestBuilder.append("&is_trading=").append(params.getTrading() ? 1 : 0);
        if (params.getGroupBy() != null)
            requestBuilder.append("&group_by=").append(params.getGroupBy());
        if (params.getGroupByFilter() != null)
        {
            Validate.notNull(params.getGroupBy());
            requestBuilder.append("&group_by_filter=").append(params.getGroupByFilter());
        }

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.SECURITY, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/5

    // BEGIN --> https://iss.moex.com/iss/reference/13

    /**
     * Получить спецификацию инструмента
     */
    public SecurityInfoResponse getInfo(InfoSecurityParams params) throws IOException
    {
        Response response = getInfoResponse(params);
        return SecurityInfoResponse.builder()
                .descriptions(ResponseUtils.convertTo(EntityType.DESCRIPTION, response))
                .boards(ResponseUtils.convertTo(EntityType.BOARD, response))
                .build();
    }

    /**
     * Описание инструментов
     */
    public List<DescriptionResponse> getDescriptionsInfo(InfoSecurityParams params) throws IOException
    {
        Response response = getInfoResponse(params, "description");
        return ResponseUtils.convertTo(EntityType.DESCRIPTION, response);
    }

    /**
     * Режимы, на которых торгуется инструмент
     */
    public List<BoardResponse> getBoardsInfo(InfoSecurityParams params) throws IOException
    {
        Response response = getInfoResponse(params, "boards");
        return ResponseUtils.convertTo(EntityType.BOARD, response);
    }

    private Response getInfoResponse(InfoSecurityParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getSecurity());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/securities/").append(params.getSecurity()).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        requestBuilder.append("&lang=").append(params.getLanguage());

        if (params.getStartIndex() != null)
            requestBuilder.append("&boards.start=").append(params.getStartIndex());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/13

    // BEGIN --> https://iss.moex.com/iss/reference/160

    /**
     * Список индексов в которые входит бумага
     */
    public List<IndexResponse> getIndices(IndicesSecurityParams params) throws IOException
    {
        Validate.notBlank(params.getSecurity());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/securities/").append(params.getSecurity()).append("/indices.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage())
                .append("&only_actual=").append(params.isOnlyActual() ? 1 : 0);

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.INDEX, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/160
}
