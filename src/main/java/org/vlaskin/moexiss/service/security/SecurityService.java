package org.vlaskin.moexiss.service.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.DescriptionResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.SecurityInfoResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.security.params.IndicesSecurityParams;
import org.vlaskin.moexiss.service.security.params.InfoSecurityParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;

import java.io.IOException;
import java.util.List;

/** Сервис поиска инструментов и получения их спецификаций и состава индексов. */
@Slf4j
public final class SecurityService extends BaseService
{
    /** Создаёт сервис для официального адреса MOEX ISS. */
    public SecurityService()
    {
        super();
    }

    /**
     * Создаёт сервис для заданного адреса и транспорта.
     *
     * @param baseUrl базовый адрес сервера
     * @param httpTransport HTTP-транспорт
     */
    public SecurityService(String baseUrl, MoexHttpTransport httpTransport)
    {
        super(baseUrl, httpTransport);
    }

    // BEGIN --> https://iss.moex.com/iss/reference/5

    /**
     * Список бумаг торгуемых на московской бирже
     */
    public List<SecurityResponse> getList(ListSecurityParams params) throws IOException
    {
        requireParams(params);
        requireParameter(params.getLanguage(), "language");
        int startIndex = calculateStartIndex(params.getPageIndex(), params.getLimit());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/securities.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        appendQueryParameter(requestBuilder, "start", startIndex);
        appendQueryParameter(requestBuilder, "limit", params.getLimit());

        if (params.getQuery() != null)
            appendQueryParameter(requestBuilder, "q", params.getQuery());
        if (params.getEngine() != null)
            appendQueryParameter(requestBuilder, "engine", params.getEngine());
        if (params.getMarket() != null)
            appendQueryParameter(requestBuilder, "market", params.getMarket());
        if (params.getTrading() != null)
            appendQueryParameter(requestBuilder, "is_trading", params.getTrading() ? 1 : 0);
        if (params.getGroupBy() != null)
            appendQueryParameter(requestBuilder, "group_by", params.getGroupBy());
        if (params.getGroupByFilter() != null)
        {
            Validate.notNull(params.getGroupBy(),
                    "Parameter groupBy is required when groupByFilter is used");
            appendQueryParameter(requestBuilder, "group_by_filter", params.getGroupByFilter());
        }

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.SECURITY,
                getAndParseResponse(requestBuilder, Response.class));
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
        requireParams(params);
        requireTextParameter(params.getSecurity(), "security");
        requireParameter(params.getLanguage(), "language");
        if (params.getStartIndex() != null)
            requireNonNegative(params.getStartIndex(), "startIndex");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/securities/")
                .append(encodePathSegment(params.getSecurity())).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        if (params.getStartIndex() != null)
            appendQueryParameter(requestBuilder, "boards.start", params.getStartIndex());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/13

    // BEGIN --> https://iss.moex.com/iss/reference/160

    /**
     * Список индексов в которые входит бумага
     */
    public List<IndexResponse> getIndices(IndicesSecurityParams params) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getSecurity(), "security");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/securities/")
                .append(encodePathSegment(params.getSecurity())).append("/indices.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        appendQueryParameter(requestBuilder, "only_actual", params.isOnlyActual() ? 1 : 0);

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.INDEX,
                getAndParseResponse(requestBuilder, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/160
}
