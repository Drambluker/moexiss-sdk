package org.vlaskin.moexiss.service.statistic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.fluent.Request;
import org.vlaskin.moexiss.entity.*;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.IndicesStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickerInfoStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickersStatisticParams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class StatisticService extends BaseService
{
    // BEGIN --> https://iss.moex.com/iss/reference/146

    /**
     * Список индексов доступных для просмотра аналитических показателей с доступными датами
     */
    public List<IndexResponse> getIndices(IndicesStatisticParams params) throws IOException
    {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/statistics/engines/stock/markets/index/analytics.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage())
                .append("&tradingsession=").append(params.getTradingSession());

        if (params.getSecurityCollection() != null)
            requestBuilder.append("&security_collection=").append(params.getSecurityCollection());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.INDEX, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/146

    // BEGIN --> https://iss.moex.com/iss/reference/147

    /**
     * Аналитические показатели за дату
     */
    public IndexAnalyticsResponse getIndexAnalytics(AnalyticsStatisticParams params) throws IOException
    {
        Response response = getIndexAnalyticsResponse(params);
        return IndexAnalyticsResponse.builder()
                .data(ResponseUtils.convertTo(EntityType.INDEX_ANALYTICS, response))
                .cursors(ResponseUtils.convertTo(EntityType.CURSOR, response))
                .dates(ResponseUtils.convertTo(EntityType.INDEX_ANALYTICS_DATES, response))
                .build();
    }

    /**
     * Аналитика по индексам
     */
    public List<IndexAnalyticsDataResponse> getIndexAnalyticsData(AnalyticsStatisticParams params) throws IOException
    {
        Response response = getIndexAnalyticsResponse(params, "analytics");
        return ResponseUtils.convertTo(EntityType.INDEX_ANALYTICS, response);
    }

    public List<CursorResponse> getIndexAnalyticsCursor(AnalyticsStatisticParams params) throws IOException
    {
        Response response = getIndexAnalyticsResponse(params, "analytics.cursor");
        return ResponseUtils.convertTo(EntityType.CURSOR, response);
    }

    /**
     * Интервал дат доступных данных для аналитических показателей индекса
     */
    public List<IndexAnalyticsDatesResponse> getIndexAnalyticsDates(AnalyticsStatisticParams params) throws IOException
    {
        Response response = getIndexAnalyticsResponse(params, "analytics.dates");
        return ResponseUtils.convertTo(EntityType.INDEX_ANALYTICS_DATES, response);
    }

    private Response getIndexAnalyticsResponse(AnalyticsStatisticParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getIndex());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/statistics/engines/stock/markets/index/analytics/").append(params.getIndex()).append(".json");
        pasteBasicRequestParams(requestBuilder, only);

        if (params.getLanguage() != null)
            requestBuilder.append("&lang=").append(params.getLanguage());
        requestBuilder.append("&start=").append(params.getPageIndex() * params.getLimit());
        requestBuilder.append("&limit=").append(params.getLimit());
        if (params.getTradingSession() != null)
            requestBuilder.append("&tradingsession=").append(params.getTradingSession());
        if (params.getDate() != null)
            requestBuilder.append("&date=").append(params.getDate());
        if (params.getTickers() != null)
            requestBuilder.append("&tickers=").append(params.getTickers());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/147

    // BEGIN --> https://iss.moex.com/iss/reference/148

    /**
     * Список тикеров для индекса за все периоды
     */
    public List<TickerResponse> getTickers(TickersStatisticParams params) throws IOException
    {
        Validate.notBlank(params.getIndex());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/statistics/engines/stock/markets/index/analytics/").append(params.getIndex())
                .append("/tickers.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&tradingsession=").append(params.getTradingSession());

        if (params.getDate() != null)
            requestBuilder.append("&date=").append(params.getDate());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.TICKER, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/148

    // BEGIN --> https://iss.moex.com/iss/reference/149

    /**
     * Информация по тикеру
     */
    public TickerInfoResponse getTickerInfo(TickerInfoStatisticParams params) throws IOException
    {
        Response response = getTickerInfoResponse(params);
        return TickerInfoResponse.builder()
                .tickers(ResponseUtils.convertTo(EntityType.TICKER, response))
                .cursors(ResponseUtils.convertTo(EntityType.CURSOR, response))
                .build();
    }

    public List<TickerResponse> getTickerInfoData(TickerInfoStatisticParams params) throws IOException
    {
        Response response = getTickerInfoResponse(params, "ticker");
        return ResponseUtils.convertTo(EntityType.TICKER, response);
    }

    public List<CursorResponse> getTickerInfoCursor(TickerInfoStatisticParams params) throws IOException
    {
        Response response = getTickerInfoResponse(params, "ticker.cursor");
        return ResponseUtils.convertTo(EntityType.CURSOR, response);
    }

    private Response getTickerInfoResponse(TickerInfoStatisticParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getIndex());
        Validate.notBlank(params.getTicker());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/statistics/engines/stock/markets/index/analytics/").append(params.getIndex())
                .append("/tickers/").append(params.getTicker()).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        requestBuilder.append("&lang=").append(params.getLanguage())
                .append("&tradingsession=").append(params.getTradingSession())
                .append("&start=").append(params.getStartIndex())
                .append("&from=").append(params.getFrom())
                .append("&till=").append(params.getTill());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/149
}
