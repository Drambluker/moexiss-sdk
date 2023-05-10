package org.vlaskin.moexiss.service.engine;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.hc.client5.http.fluent.Request;
import org.vlaskin.moexiss.entity.Board;
import org.vlaskin.moexiss.entity.BoardGroup;
import org.vlaskin.moexiss.entity.DailyTableRecord;
import org.vlaskin.moexiss.entity.DataVersion;
import org.vlaskin.moexiss.entity.Engine;
import org.vlaskin.moexiss.entity.EngineInfo;
import org.vlaskin.moexiss.entity.Market;
import org.vlaskin.moexiss.entity.MarketData;
import org.vlaskin.moexiss.entity.MarketDataYield;
import org.vlaskin.moexiss.entity.MarketInfo;
import org.vlaskin.moexiss.entity.SecuritiesTable;
import org.vlaskin.moexiss.entity.Security;
import org.vlaskin.moexiss.entity.SecurityTable;
import org.vlaskin.moexiss.entity.TableField;
import org.vlaskin.moexiss.entity.TimeTableRecord;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.response.field.FieldResponse;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.engine.params.BoardEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.BoardsEngineParams;
import org.vlaskin.moexiss.service.engine.params.InfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.ListEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketInfoEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecuritiesTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketSecurityTableEngineParams;
import org.vlaskin.moexiss.service.engine.params.MarketsEngineParams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class EngineService extends BaseService
{
    // BEGIN --> https://iss.moex.com/iss/reference/40

    /**
     * Список доступных торговых систем
     */
    public List<Engine> getList(ListEngineParams params) throws IOException
    {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/40

    // BEGIN --> https://iss.moex.com/iss/reference/41

    /**
     * Получить описание и режим работы торговой системы
     */
    public EngineInfo getInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params);
        EngineInfo.EngineInfoBuilder engineInfoBuilder = EngineInfo.builder()
                .timeTable(ResponseUtils.convertTo(EntityType.TIME_TABLE, response))
                .dailyTable(ResponseUtils.convertTo(EntityType.DAILY_TABLE, response));
        List<Engine> engines = ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, response);
        if (engines != null && !engines.isEmpty())
            engineInfoBuilder.engine(engines.get(0));
        return engineInfoBuilder.build();
    }

    /**
     * Общие атрибуты ТС
     */
    public Engine getEngineInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "engine");
        List<Engine> engines = ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, response);
        return engines != null && !engines.isEmpty() ? engines.get(0) : null;
    }

    /**
     * Недельное расписание работы ТС
     */
    public List<TimeTableRecord> getTimeTableInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "timetable");
        return ResponseUtils.convertTo(EntityType.TIME_TABLE, response);
    }

    /**
     * Работа ТС вне недельного расписания, например в праздничные или предпраздничные дни
     */
    public List<DailyTableRecord> getDailyTableInfo(InfoEngineParams params) throws IOException
    {
        Response response = getInfoResponse(params, "dailytable");
        return ResponseUtils.convertTo(EntityType.DAILY_TABLE, response);
    }

    private Response getInfoResponse(InfoEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine()).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        requestBuilder.append("&lang=").append(params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/41

    // BEGIN --> https://iss.moex.com/iss/reference/42

    /**
     * Справочник доступных рынков и их атрибуты
     */
    public List<Market> getMarkets(MarketsEngineParams params) throws IOException
    {
        Validate.notBlank(params.getEngine());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine()).append("/markets.json");
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return ResponseUtils.convertTo(EntityType.MARKET, gson.fromJson(responseString, Response.class));
    }
    // END --> https://iss.moex.com/iss/reference/42

    // BEGIN --> https://iss.moex.com/iss/reference/44

    /**
     * Получить описание: словарь доступных режимов торгов, описание полей публикуемых таблиц данных и т.д.
     */
    public MarketInfo getMarketInfo(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params);
        return MarketInfo.builder()
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
    public List<Board> getMarketBoards(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "boards");
        return ResponseUtils.convertTo(EntityType.BOARD, response);
    }

    /**
     * Группы режимов торгов
     */
    public List<BoardGroup> getMarketBoardGroups(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "boardgroups");
        return ResponseUtils.convertTo(EntityType.BOARD_GROUP, response);
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<TableField> getMarketSecurityFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY_FIELD, response);
    }

    /**
     * Справочник полей таблицы котировок
     */
    public List<TableField> getMarketDataFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_FIELD, response);
    }

    /**
     * Справочник полей таблицы сделок торговой сессии рынка
     */
    public List<TableField> getMarketTradeFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades");
        return ResponseUtils.convertTo(EntityType.TRADE_FIELD, response);
    }

    /**
     * Справочник полей таблицы с котировками (стакана заявок)
     */
    public List<TableField> getMarketOrderBookFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "orderbook");
        return ResponseUtils.convertTo(EntityType.ORDER_BOOK_FIELD, response);
    }

    /**
     * Справочник полей истории таблицы инструментов
     */
    public List<TableField> getMarketHistoryFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "history");
        return ResponseUtils.convertTo(EntityType.HISTORY_FIELD, response);
    }

    /**
     * Справочник полей таблицы сделок архив реестра сделок
     */
    public List<TableField> getMarketTradeHistoryFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades_hist");
        return ResponseUtils.convertTo(EntityType.TRADE_HISTORY_FIELD, response);
    }

    public List<TableField> getMarketDataYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD_FIELD, response);
    }

    public List<TableField> getMarketTradeYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "trades_yields");
        return ResponseUtils.convertTo(EntityType.TRADE_YIELD_FIELD, response);
    }

    public List<TableField> getMarketHistoryYieldFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "history_yields");
        return ResponseUtils.convertTo(EntityType.HISTORY_YIELD_FIELD, response);
    }

    /**
     * Описание полей таблицы "Итоги для "
     */
    public List<TableField> getMarketSecurityStatisticFields(MarketInfoEngineParams params) throws IOException
    {
        Response response = getMarketInfoResponse(params, "secstats");
        return ResponseUtils.convertTo(EntityType.SECURITY_STATISTIC_FIELD, response);
    }

    private Response getMarketInfoResponse(MarketInfoEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket()).append(".json");
        pasteBasicRequestParams(requestBuilder, only);
        requestBuilder.append("&lang=").append(params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, FieldResponse.class);
    }
    // END --> https://iss.moex.com/iss/reference/44

    // BEGIN --> https://iss.moex.com/iss/reference/33

    /**
     * Получить таблицу инструментов торговой сессии по рынку в целом
     */
    public SecuritiesTable getMarketSecuritiesTable(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params);
        return SecuritiesTable.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<Security> getMarketSecurities(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketData> getMarketData(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersion> getMarketDataVersions(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYield> getMarketDataYields(MarketSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getMarketSecuritiesTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getMarketSecuritiesTableResponse(MarketSecuritiesTableEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket()).append("/securities.json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/33

    // BEGIN --> https://iss.moex.com/iss/reference/52

    /**
     * Получить данные по конкретному инструменту рынка
     */
    public SecuritiesTable getMarketSecuritiesTable(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params);
        return SecuritiesTable.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<Security> getMarketSecurities(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketData> getMarketData(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersion> getMarketDataVersions(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYield> getMarketDataYields(MarketSecurityTableEngineParams params) throws IOException
    {
        Response response = getMarketSecurityTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getMarketSecurityTableResponse(MarketSecurityTableEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());
        Validate.notBlank(params.getSecurity());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket()).append("/securities/")
                .append(params.getSecurity()).append(".json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/52

    // BEGIN --> https://iss.moex.com/iss/reference/43

    /**
     * Доступные режимы торгов рынка
     */
    public List<Board> getBoards(BoardsEngineParams params) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket()).append("/boards.json");

        Response response = getBasicBoardsResponse(params, requestBuilder);
        return ResponseUtils.convertTo(EntityType.BOARD, response);
    }
    // END --> https://iss.moex.com/iss/reference/43

    // BEGIN --> https://iss.moex.com/iss/reference/49

    /**
     * Описание режима торгов
     */
    public Board getBoard(BoardEngineParams params) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());
        Validate.notBlank(params.getBoard());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket())
                .append("/boards/").append(params.getBoard()).append(".json");

        Response response = getBasicBoardsResponse(params, requestBuilder);
        List<Board> boards = ResponseUtils.convertTo(EntityType.BOARD, response);
        return boards != null && !boards.isEmpty() ? boards.get(0) : null;
    }
    // END --> https://iss.moex.com/iss/reference/49

    private Response getBasicBoardsResponse(BoardsEngineParams params, StringBuilder requestBuilder) throws IOException
    {
        pasteBasicRequestParams(requestBuilder);
        requestBuilder.append("&lang=").append(params.getLanguage());

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }

    // BEGIN --> https://iss.moex.com/iss/reference/32

    /**
     * Получить таблицу инструментов по режиму торгов
     */
    public SecuritiesTable getBoardSecuritiesTable(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params);
        return SecuritiesTable.builder()
                .securities(ResponseUtils.convertTo(EntityType.SECURITY, response))
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response))
                .build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public List<Security> getBoardSecurities(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "securities");
        return ResponseUtils.convertTo(EntityType.SECURITY, response);
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketData> getBoardMarketData(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersion> getBoardDataVersions(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYield> getBoardMarketDataYields(BoardSecuritiesTableEngineParams params) throws IOException
    {
        Response response = getBoardSecuritiesTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getBoardSecuritiesTableResponse(BoardSecuritiesTableEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());
        Validate.notBlank(params.getBoard());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket())
                .append("/boards/").append(params.getBoard()).append("/securities.json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/32

    // BEGIN --> https://iss.moex.com/iss/reference/53

    /**
     * Получить данные по указанному инструменту на выбранном режиме торгов
     */
    public SecurityTable getBoardSecurityTable(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params);
        List<Security> securities = ResponseUtils.convertTo(EntityType.SECURITY, response);
        SecurityTable.SecurityTableBuilder securityTableBuilder = SecurityTable.builder()
                .marketData(ResponseUtils.convertTo(EntityType.MARKET_DATA, response))
                .dataVersions(ResponseUtils.convertTo(EntityType.DATA_VERSION, response))
                .marketDataYields(ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response));
        if (securities != null && !securities.isEmpty())
            securityTableBuilder.security(securities.get(0));
        return securityTableBuilder.build();
    }

    /**
     * Справочник полей таблицы со статическими данными торговой сессии рынка
     */
    public Security getBoardSecurity(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "securities");
        List<Security> securities = ResponseUtils.convertTo(EntityType.SECURITY, response);
        return securities != null && !securities.isEmpty() ? securities.get(0) : null;
    }

    /**
     * Данные с текущими значениями инструментов рынка
     */
    public List<MarketData> getBoardMarketData(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "marketdata");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA, response);
    }

    /**
     * Версионность данных
     */
    public List<DataVersion> getBoardDataVersions(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "dataversion");
        return ResponseUtils.convertTo(EntityType.DATA_VERSION, response);
    }

    /**
     * Доходности
     */
    public List<MarketDataYield> getBoardMarketDataYields(BoardSecurityTableEngineParams params) throws IOException
    {
        Response response = getBoardSecurityTableResponse(params, "marketdata_yields");
        return ResponseUtils.convertTo(EntityType.MARKET_DATA_YIELD, response);
    }

    private Response getBoardSecurityTableResponse(BoardSecurityTableEngineParams params, String... only) throws IOException
    {
        Validate.notBlank(params.getEngine());
        Validate.notBlank(params.getMarket());
        Validate.notBlank(params.getBoard());
        Validate.notBlank(params.getSecurity());

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(BASE_URL).append("/iss/engines/").append(params.getEngine())
                .append("/markets/").append(params.getMarket())
                .append("/boards/").append(params.getBoard())
                .append("/securities/").append(params.getSecurity()).append(".json");
        pasteSecuritiesTableParams(requestBuilder, params, only);

        log.debug("Request: {}", requestBuilder);
        String responseString = Request.get(requestBuilder.toString()).execute().returnContent().asString(StandardCharsets.UTF_8);
        log.debug("Response: {}", responseString);
        return gson.fromJson(responseString, Response.class);
    }
    // END --> https://iss.moex.com/iss/reference/53

    private void pasteSecuritiesTableParams(StringBuilder requestBuilder, MarketSecuritiesTableEngineParams params, String[] only)
    {
        pasteBasicRequestParams(requestBuilder, only);
        requestBuilder.append("&lang=").append(params.getLanguage())
                .append("&first=").append(params.getFirst());
        if (params.getSortColumn() != null)
            requestBuilder.append("&sort_column=").append(params.getSortColumn());
        requestBuilder.append("&sort_order=").append(params.getSortOrder())
                .append("&leaders=").append(params.isLeaders() ? 1 : 0)
                .append("&nearest=").append(params.isNearest() ? 1 : 0)
                .append("&previous_session=").append(params.isPreviousSession() ? 1 : 0);
        if (params.getPrimaryBoard() != null)
            requestBuilder.append("&primary_board=").append(params.getPrimaryBoard());
        if (params.getAssets() != null && !params.getAssets().isEmpty())
            requestBuilder.append("&assets=").append(String.join(",", params.getAssets()));
        if (params.getIndex() != null)
            requestBuilder.append("&index=").append(params.getIndex());
        if (params.getSecurities() != null && !params.getSecurities().isEmpty())
            requestBuilder.append("&securities=").append(String.join(",", params.getSecurities()));
        if (params.getSecurityTypes() != null && !params.getSecurityTypes().isEmpty())
            requestBuilder.append("&sectypes=").append(String.join(",", params.getSecurityTypes()));
        if (params.getSecurityCollection() != null)
            requestBuilder.append("&security_collection=").append(params.getSecurityCollection());
    }
}
