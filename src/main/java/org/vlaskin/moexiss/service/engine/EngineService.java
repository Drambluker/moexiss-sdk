package org.vlaskin.moexiss.service.engine;

import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.entity.*;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.response.field.FieldResponse;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.engine.params.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** Сервис торговых систем, рынков, режимов торгов и таблиц инструментов. */
@Slf4j
public final class EngineService extends BaseService
{
    /** Создаёт сервис для официального адреса MOEX ISS. */
    public EngineService()
    {
        super();
    }

    /**
     * Создаёт сервис для заданного адреса и транспорта.
     *
     * @param baseUrl базовый адрес сервера
     * @param httpTransport HTTP-транспорт
     */
    public EngineService(String baseUrl, MoexHttpTransport httpTransport)
    {
        super(baseUrl, httpTransport);
    }

    // BEGIN --> https://iss.moex.com/iss/reference/40

    /**
     * Список доступных торговых систем
     */
    public List<EngineResponse> getList(ListEngineParams params) throws IOException
    {
        requireParams(params);
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.TRADING_SYSTEM,
                getAndParseResponse(requestBuilder, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/40

    // BEGIN --> https://iss.moex.com/iss/reference/41

    /**
     * Получить описание и режим работы торговой системы
     */
    public EngineInfoResponse getInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params);
        EngineInfoResponse.EngineInfoResponseBuilder engineInfoBuilder = EngineInfoResponse.builder()
                .timeTable(ResponseUtils.convertTo(EntityType.TIME_TABLE, response))
                .dailyTable(ResponseUtils.convertTo(EntityType.DAILY_TABLE, response));
        List<EngineResponse> engines = ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, response);
        if (!engines.isEmpty())
            engineInfoBuilder.engine(engines.getFirst());
        return engineInfoBuilder.build();
    }

    /**
     * Общие атрибуты ТС
     */
    public Optional<EngineResponse> getEngineInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "engine");
        List<EngineResponse> engines = ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, response);
        return !engines.isEmpty() ? Optional.of(engines.getFirst()) : Optional.empty();
    }

    /**
     * Недельное расписание работы ТС
     */
    public List<TimeTableRecordResponse> getTimeTableInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "timetable");
        return ResponseUtils.convertTo(EntityType.TIME_TABLE, response);
    }

    /**
     * Работа ТС вне недельного расписания, например в праздничные или предпраздничные дни
     */
    public List<DailyTableRecordResponse> getDailyTableInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "dailytable");
        return ResponseUtils.convertTo(EntityType.DAILY_TABLE, response);
    }

    private Response getInfoResponse(InfoEngineParams params, String... only) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/41

    // BEGIN --> https://iss.moex.com/iss/reference/42

    /**
     * Справочник доступных рынков и их атрибуты
     */
    public List<MarketResponse> getMarkets(MarketsEngineParams params) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets.json");
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        return ResponseUtils.convertTo(EntityType.MARKET,
                getAndParseResponse(requestBuilder, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/42

    // BEGIN --> https://iss.moex.com/iss/reference/44

    /**
     * Получить описание: словарь доступных режимов торгов, описание полей публикуемых таблиц данных и т.д.
     */
    public MarketInfoResponse getMarketInfo(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params);
        return MarketInfoResponse.builder()
                .boards(ResponseUtils.convertTo(EntityType.BOARD, response))
                .boardGroups(ResponseUtils.convertTo(EntityType.BOARD_GROUP, response))
                .securityFields(ResponseUtils.convertTo(EntityType.SECURITY_FIELD, response))
                .marketDataFields(ResponseUtils.convertTo(EntityType.MARKET_DATA_FIELD, response))
                .tradeFields(ResponseUtils.convertTo(EntityType.TRADE_FIELD, response))
                .orderBookFields(ResponseUtils.convertTo(EntityType.ORDER_BOOK_FIELD, response))
                .historyFields(ResponseUtils.convertTo(EntityType.HISTORY_FIELD, response))
                .tradeHistoryFields(ResponseUtils.convertTo(EntityType.TRADE_HISTORY_FIELD, response))
                .marketDataYieldFields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD_FIELD, response))
                .tradeYieldFields(ResponseUtils.convertTo(EntityType.TRADE_YIELD_FIELD, response))
                .historyYieldFields(ResponseUtils.convertTo(EntityType.HISTORY_YIELD_FIELD, response))
                .securityStatisticFields(ResponseUtils.convertTo(EntityType.SECURITY_STATISTIC_FIELD, response))
                .build();
    }

    /**
     * Доступные режимы торгов рынка
     */
    public List<BoardResponse> getMarketBoards(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "boards");
        return ResponseUtils.convertTo(EntityType.BOARD, response);
    }

    /**
     * Группы режимов торгов
     */
    public List<BoardGroupResponse> getMarketBoardGroups(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "boardgroups");
        return ResponseUtils.convertTo(EntityType.BOARD_GROUP, response);
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<TableFieldResponse> getMarketSecurityFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY_FIELD, response);
    }

    /**
     * Справочник полей таблицы котировок
     */
    public List<TableFieldResponse> getMarketDataFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_FIELD, response);
    }

    /**
     * Справочник полей таблицы сделок торговой сессии рынка
     */
    public List<TableFieldResponse> getMarketTradeFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades");
        return ResponseUtils.convertTo(EntityType.TRADE_FIELD, response);
    }

    /**
     * Справочник полей таблицы с котировками (стакана заявок)
     */
    public List<TableFieldResponse> getMarketOrderBookFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "orderbook");
        return ResponseUtils.convertTo(EntityType.ORDER_BOOK_FIELD, response);
    }

    /**
     * Справочник полей истории таблицы инструментов
     */
    public List<TableFieldResponse> getMarketHistoryFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "history");
        return ResponseUtils.convertTo(EntityType.HISTORY_FIELD, response);
    }

    /**
     * Справочник полей таблицы сделок архив реестра сделок
     */
    public List<TableFieldResponse> getMarketTradeHistoryFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades_hist");
        return ResponseUtils.convertTo(EntityType.TRADE_HISTORY_FIELD, response);
    }

    /** Возвращает описание полей таблицы доходностей текущих рыночных данных. */
    public List<TableFieldResponse> getMarketDataYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD_FIELD, response);
    }

    /** Возвращает описание полей таблицы доходностей сделок. */
    public List<TableFieldResponse> getMarketTradeYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades_yields");
        return ResponseUtils.convertTo(EntityType.TRADE_YIELD_FIELD, response);
    }

    /** Возвращает описание полей исторической таблицы доходностей. */
    public List<TableFieldResponse> getMarketHistoryYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "history_yields");
        return ResponseUtils.convertTo(EntityType.HISTORY_YIELD_FIELD, response);
    }

    /**
     * Описание полей таблицы "Итоги для "
     */
    public List<TableFieldResponse> getMarketSecurityStatisticFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "secstats");
        return ResponseUtils.convertTo(EntityType.SECURITY_STATISTIC_FIELD, response);
    }

    private Response getMarketInfoResponse(MarketInfoEngineParams params, String... only) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, FieldResponse.class);
    }
    // END --> https://iss.moex.com/iss/reference/44

    // BEGIN --> https://iss.moex.com/iss/reference/33

    /**
     * Получить таблицу инструментов торговой сессии по рынку в целом
     */
    public SecuritiesTableResponse getMarketSecuritiesTable(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params);
        return SecuritiesTableResponse.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<SecurityResponse> getMarketSecurities(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketDataResponse> getMarketData(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersionResponse> getMarketDataVersions(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYieldResponse> getMarketDataYields(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getMarketSecuritiesTableResponse(MarketSecuritiesTableEngineParams params, String... only) throws IOException
    {
        validateSecuritiesTableParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/securities.json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/33

    // BEGIN --> https://iss.moex.com/iss/reference/52

    /**
     * Получить данные по конкретному инструменту рынка
     */
    public SecuritiesTableResponse getMarketSecuritiesTable(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params);
        return SecuritiesTableResponse.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<SecurityResponse> getMarketSecurities(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketDataResponse> getMarketData(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersionResponse> getMarketDataVersions(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYieldResponse> getMarketDataYields(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getMarketSecurityTableResponse(MarketSecurityTableEngineParams params, String... only) throws IOException
    {
        validateSecuritiesTableParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireTextParameter(params.getSecurity(), "security");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/securities/")
                .append(encodePathSegment(params.getSecurity())).append(".json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/52

    // BEGIN --> https://iss.moex.com/iss/reference/43

    /**
     * Доступные режимы торгов рынка
     */
    public List<BoardResponse> getBoards(BoardsEngineParams params) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/boards.json");

        Response response = getBasicBoardsResponse(params, requestBuilder);
        return ResponseUtils.convertTo(EntityType.BOARD, response);
    }
    // END --> https://iss.moex.com/iss/reference/43

    // BEGIN --> https://iss.moex.com/iss/reference/49

    /**
     * Описание режима торгов
     */
    public Optional<BoardResponse> getBoard(BoardEngineParams params) throws IOException
    {
        requireParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireTextParameter(params.getBoard(), "board");
        requireParameter(params.getLanguage(), "language");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/boards/")
                .append(encodePathSegment(params.getBoard())).append(".json");

        Response response = getBasicBoardsResponse(params, requestBuilder);
        List<BoardResponse> boards = ResponseUtils.convertTo(EntityType.BOARD, response);
        return !boards.isEmpty() ? Optional.of(boards.getFirst()) : Optional.empty();
    }
    // END --> https://iss.moex.com/iss/reference/49

    private Response getBasicBoardsResponse(BoardsEngineParams params, StringBuilder requestBuilder) throws IOException
    {
        pasteBasicRequestParams(requestBuilder);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }

    // BEGIN --> https://iss.moex.com/iss/reference/32

    /**
     * Получить таблицу инструментов по режиму торгов
     */
    public SecuritiesTableResponse getBoardSecuritiesTable(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params);
        return SecuritiesTableResponse.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<SecurityResponse> getBoardSecurities(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketDataResponse> getBoardMarketData(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersionResponse> getBoardDataVersions(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYieldResponse> getBoardMarketDataYields(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getBoardSecuritiesTableResponse(BoardSecuritiesTableEngineParams params, String... only) throws IOException
    {
        validateSecuritiesTableParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireTextParameter(params.getBoard(), "board");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/boards/")
                .append(encodePathSegment(params.getBoard())).append("/securities.json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/32

    // BEGIN --> https://iss.moex.com/iss/reference/53

    /**
     * Получить данные по указанному инструменту на выбранном режиме торгов
     */
    public SecurityTableResponse getBoardSecurityTable(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params);
        List<SecurityResponse> securities = ResponseUtils.convertTo(EntityType.SECURITY, response);
        SecurityTableResponse.SecurityTableResponseBuilder securityTableBuilder = SecurityTableResponse.builder()
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response));
        if (!securities.isEmpty())
            securityTableBuilder.security(securities.getFirst());
        return securityTableBuilder.build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public Optional<SecurityResponse> getBoardSecurity(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "securities");
        List<SecurityResponse> securities = ResponseUtils.convertTo(EntityType.SECURITY, response);
        return !securities.isEmpty() ? Optional.of(securities.getFirst()) : Optional.empty();
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketDataResponse> getBoardMarketData(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersionResponse> getBoardDataVersions(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYieldResponse> getBoardMarketDataYields(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getBoardSecurityTableResponse(BoardSecurityTableEngineParams params, String... only) throws IOException
    {
        validateSecuritiesTableParams(params);
        requireTextParameter(params.getEngine(), "engine");
        requireTextParameter(params.getMarket(), "market");
        requireTextParameter(params.getBoard(), "board");
        requireTextParameter(params.getSecurity(), "security");

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(baseUrl).append("/iss/engines/")
                .append(encodePathSegment(params.getEngine())).append("/markets/")
                .append(encodePathSegment(params.getMarket())).append("/boards/")
                .append(encodePathSegment(params.getBoard())).append("/securities/")
                .append(encodePathSegment(params.getSecurity())).append(".json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/53

    private void pasteSecuritiesTableParams(StringBuilder requestBuilder, MarketSecuritiesTableEngineParams params, String[] only)
    {
        pasteBasicRequestParams(requestBuilder, only);
        appendQueryParameter(requestBuilder, "lang", params.getLanguage());
        appendQueryParameter(requestBuilder, "first", params.getFirst());
        if (params.getSortColumn() != null)
            appendQueryParameter(requestBuilder, "sort_column", params.getSortColumn());
        appendQueryParameter(requestBuilder, "sort_order", params.getSortOrder());
        appendQueryParameter(requestBuilder, "leaders", params.isLeaders() ? 1 : 0);
        appendQueryParameter(requestBuilder, "nearest", params.isNearest() ? 1 : 0);
        appendQueryParameter(requestBuilder, "previous_session", params.isPreviousSession() ? 1 : 0);
        if (params.getPrimaryBoard() != null)
            appendQueryParameter(requestBuilder, "primary_board", params.getPrimaryBoard());
        if (params.getAssets() != null && !params.getAssets().isEmpty())
            appendQueryParameter(requestBuilder, "assets", params.getAssets());
        if (params.getIndex() != null)
            appendQueryParameter(requestBuilder, "index", params.getIndex());
        if (params.getSecurities() != null && !params.getSecurities().isEmpty())
            appendQueryParameter(requestBuilder, "securities", params.getSecurities());
        if (params.getSecurityTypes() != null && !params.getSecurityTypes().isEmpty())
            appendQueryParameter(requestBuilder, "sectypes", params.getSecurityTypes());
        if (params.getSecurityCollection() != null)
            appendQueryParameter(requestBuilder, "security_collection", params.getSecurityCollection());
    }

    private static void validateSecuritiesTableParams(MarketSecuritiesTableEngineParams params)
    {
        requireParams(params);
        requireParameter(params.getLanguage(), "language");
        requireParameter(params.getSortOrder(), "sortOrder");
        requireNonNegative(params.getFirst(), "first");
    }
}
