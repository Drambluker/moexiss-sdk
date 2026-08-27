package org.vlaskin.moexiss.service.statistic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.vlaskin.moexiss.entity.*;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.statistic.params.AnalyticsStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.IndicesStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickerInfoStatisticParams;
import org.vlaskin.moexiss.service.statistic.params.TickersStatisticParams;

import java.io.IOException;
import java.util.List;

/** Сервис аналитики индексов и истории входящих в них инструментов. */
@Slf4j
public final class StatisticService extends BaseService
{
    /** Создаёт сервис для официального адреса MOEX ISS. */
    public StatisticService()
    {
        super();
    }

    /**
     * Создаёт сервис для заданного адреса и транспорта.
     *
     * @param baseUrl базовый адрес сервера
     * @param httpTransport HTTP-транспорт
     */
    public StatisticService(String baseUrl, MoexHttpTransport httpTransport)
    {
        super(baseUrl, httpTransport);
    }

    // BEGIN --> https://iss.moex.com/iss/reference/146

    /**
     * Список индексов доступных для просмотра аналитических показателей с доступными датами
     */
    public List<IndexResponse> getIndices(IndicesStatisticParams params) throws IOException
    {
        requireParams(params);
        requireParameter(params.getLanguage(), "language");
        requireParameter(params.getTradingSession(), "tradingSession");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/statistics/engines/stock/markets/index/analytics.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        appendQueryParameter(requestBuilder, "tradingsession", params.getTradingSession());

        if (params.getSecurityCollection() != null)
            appendQueryParameter(requestBuilder, "security_collection", params.getSecurityCollection());

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.INDEX,
                getAndParseResponse(requestBuilder, Response.class));
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

    /** Возвращает данные курсора для постраничного чтения аналитики индекса. */
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
        requireParams(params);
        requireTextParameter(params.getIndex(), "index");
        requireNonNegative(params.getPageIndex(), "pageIndex");
        requirePositive(params.getLimit(), "limit");
        if (params.getStartIndex() != null)
            requireNonNegative(params.getStartIndex(), "startIndex");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/statistics/engines/stock/markets/index/analytics/")
                .append(encodePathSegment(params.getIndex())).append(".json");
        pasteBasicRequestParams(requestBuilder, only);

        if (params.getLanguage() != null)
            appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        int startIndex = params.getStartIndex() != null
                ? params.getStartIndex()
                : calculateStartIndex(params.getPageIndex(), params.getLimit());
        appendQueryParameter(requestBuilder, "start", startIndex);
        appendQueryParameter(requestBuilder, "limit", params.getLimit());
        if (params.getTradingSession() != null)
            appendQueryParameter(requestBuilder, "tradingsession", params.getTradingSession());
        if (params.getDate() != null)
            appendQueryParameter(requestBuilder, "date", params.getDate());
        if (params.getTickers() != null)
            appendQueryParameter(requestBuilder, "tickers", params.getTickers());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/147

    // BEGIN --> https://iss.moex.com/iss/reference/148

    /**
     * Список тикеров для индекса за все периоды
     */
    public List<TickerResponse> getTickers(TickersStatisticParams params) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getIndex(), "index");
        requireParameter(params.getTradingSession(), "tradingSession");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/statistics/engines/stock/markets/index/analytics/")
                .append(encodePathSegment(params.getIndex()))
                .append("/tickers.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "tradingsession", params.getTradingSession());

        if (params.getDate() != null)
            appendQueryParameter(requestBuilder, "date", params.getDate());

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.TICKER,
                getAndParseResponse(requestBuilder, Response.class));
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

    /** Возвращает историю веса инструмента в индексе. */
    public List<TickerResponse> getTickerInfoData(TickerInfoStatisticParams params) throws IOException
    {
        Response response = getTickerInfoResponse(params, "ticker");
        return ResponseUtils.convertTo(EntityType.TICKER, response);
    }

    /** Возвращает данные курсора для истории инструмента в индексе. */
    public List<CursorResponse> getTickerInfoCursor(TickerInfoStatisticParams params) throws IOException
    {
        Response response = getTickerInfoResponse(params, "ticker.cursor");
        return ResponseUtils.convertTo(EntityType.CURSOR, response);
    }

    private Response getTickerInfoResponse(TickerInfoStatisticParams params, String... only) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getIndex(), "index");
        requireTextParameter(params.getTicker(), "ticker");
        requireParameter(params.getLanguage(), "language");
        requireParameter(params.getTradingSession(), "tradingSession");
        requireNonNegative(params.getStartIndex(), "startIndex");
        requireParameter(params.getFrom(), "from");
        requireParameter(params.getTill(), "till");
        Validate.isTrue(!params.getFrom().isAfter(params.getTill()),
                "Parameter from must not be later than till");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/statistics/engines/stock/markets/index/analytics/")
                .append(encodePathSegment(params.getIndex())).append("/tickers/")
                .append(encodePathSegment(params.getTicker())).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        appendQueryParameter(requestBuilder, "tradingsession", params.getTradingSession());
        appendQueryParameter(requestBuilder, "start", params.getStartIndex());
        appendQueryParameter(requestBuilder, "from", params.getFrom());
        appendQueryParameter(requestBuilder, "till", params.getTill());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/149
}
