package org.vlaskin.moexiss.entity.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.entity.BoardGroupResponse;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.CursorResponse;
import org.vlaskin.moexiss.entity.DailyTableRecordResponse;
import org.vlaskin.moexiss.entity.DataVersionResponse;
import org.vlaskin.moexiss.entity.DescriptionResponse;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDataResponse;
import org.vlaskin.moexiss.entity.IndexAnalyticsDatesResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.MarketDataResponse;
import org.vlaskin.moexiss.entity.MarketDataYieldResponse;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.entity.TableFieldResponse;
import org.vlaskin.moexiss.entity.TickerResponse;
import org.vlaskin.moexiss.entity.TimeTableRecordResponse;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.field.FieldResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public record EntityType<T extends BasicEntity<E>, E extends Enum<?>>(AbstractProcessor<T, E> processor)
{
    private static final Map<Class<? extends Response>, Map<String, EntityType<? extends BasicEntity<?>, ? extends Enum<?>>>> REGISTRY = new HashMap<>();

    public static final EntityType<CursorResponse, CursorResponse.Fields> CURSOR = new EntityType<>(new CursorResponse.Processor());
    public static final EntityType<DataVersionResponse, DataVersionResponse.Fields> DATA_VERSION = new EntityType<>(new DataVersionResponse.Processor());
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> TABLE_FIELD = new EntityType<>(new TableFieldResponse.Processor());
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> SECURITY_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> MARKET_DATA_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> TRADE_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> ORDER_BOOK_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> HISTORY_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> TRADE_HISTORY_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> MARKET_DATA_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> TRADE_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> HISTORY_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableFieldResponse, TableFieldResponse.Fields> SECURITY_STATISTIC_FIELD = TABLE_FIELD;
    public static final EntityType<SecurityResponse, SecurityResponse.Fields> SECURITY = new EntityType<>(new SecurityResponse.Processor());
    public static final EntityType<DescriptionResponse, DescriptionResponse.Fields> DESCRIPTION = new EntityType<>(new DescriptionResponse.Processor());
    public static final EntityType<BoardResponse, BoardResponse.Fields> BOARD = new EntityType<>(new BoardResponse.Processor());
    public static final EntityType<BoardGroupResponse, BoardGroupResponse.Fields> BOARD_GROUP = new EntityType<>(new BoardGroupResponse.Processor());
    public static final EntityType<IndexResponse, IndexResponse.Fields> INDEX = new EntityType<>(new IndexResponse.Processor());
    public static final EntityType<EngineResponse, EngineResponse.Fields> TRADING_SYSTEM = new EntityType<>(new EngineResponse.Processor());
    public static final EntityType<TimeTableRecordResponse, TimeTableRecordResponse.Fields> TIME_TABLE = new EntityType<>(new TimeTableRecordResponse.Processor());
    public static final EntityType<DailyTableRecordResponse, DailyTableRecordResponse.Fields> DAILY_TABLE = new EntityType<>(new DailyTableRecordResponse.Processor());
    public static final EntityType<MarketResponse, MarketResponse.Fields> MARKET = new EntityType<>(new MarketResponse.Processor());
    public static final EntityType<MarketDataResponse, MarketDataResponse.Fields> MARKET_DATA = new EntityType<>(new MarketDataResponse.Processor());
    public static final EntityType<MarketDataYieldResponse, MarketDataYieldResponse.Fields> MARKET_DATA_YIELD = new EntityType<>(new MarketDataYieldResponse.Processor());
    public static final EntityType<IndexAnalyticsDataResponse, IndexAnalyticsDataResponse.Fields> INDEX_ANALYTICS = new EntityType<>(new IndexAnalyticsDataResponse.Processor());
    public static final EntityType<IndexAnalyticsDatesResponse, IndexAnalyticsDatesResponse.Fields> INDEX_ANALYTICS_DATES = new EntityType<>(new IndexAnalyticsDatesResponse.Processor());
    public static final EntityType<TickerResponse, TickerResponse.Fields> TICKER = new EntityType<>(new TickerResponse.Processor());

    static
    {
        REGISTRY.put(Response.class, new HashMap<>());
        REGISTRY.put(FieldResponse.class, new HashMap<>());

        REGISTRY.get(Response.class).put("dataversion", DATA_VERSION);
        REGISTRY.get(Response.class).put("securities", SECURITY);
        REGISTRY.get(Response.class).put("description", DESCRIPTION);
        REGISTRY.get(Response.class).put("boards", BOARD);
        REGISTRY.get(Response.class).put("board", BOARD);
        REGISTRY.get(Response.class).put("boardgroups", BOARD_GROUP);
        REGISTRY.get(Response.class).put("indices", INDEX);
        REGISTRY.get(Response.class).put("engines", TRADING_SYSTEM);
        REGISTRY.get(Response.class).put("engine", TRADING_SYSTEM);
        REGISTRY.get(Response.class).put("timetable", TIME_TABLE);
        REGISTRY.get(Response.class).put("dailytable", DAILY_TABLE);
        REGISTRY.get(Response.class).put("markets", MARKET);
        REGISTRY.get(Response.class).put("marketdata", MARKET_DATA);
        REGISTRY.get(Response.class).put("marketdata_yields", MARKET_DATA_YIELD);
        REGISTRY.get(Response.class).put("analytics", INDEX_ANALYTICS);
        REGISTRY.get(Response.class).put("analytics.cursor", CURSOR);
        REGISTRY.get(Response.class).put("analytics.dates", INDEX_ANALYTICS_DATES);
        REGISTRY.get(Response.class).put("tickers", TICKER);
        REGISTRY.get(Response.class).put("ticker", TICKER);
        REGISTRY.get(Response.class).put("ticker.cursor", CURSOR);

        REGISTRY.get(FieldResponse.class).put("securities", SECURITY_FIELD);
        REGISTRY.get(FieldResponse.class).put("marketdata", MARKET_DATA_FIELD);
        REGISTRY.get(FieldResponse.class).put("trades", TRADE_FIELD);
        REGISTRY.get(FieldResponse.class).put("orderbook", ORDER_BOOK_FIELD);
        REGISTRY.get(FieldResponse.class).put("history", HISTORY_FIELD);
        REGISTRY.get(FieldResponse.class).put("trades_hist", TRADE_HISTORY_FIELD);
        REGISTRY.get(FieldResponse.class).put("marketdata_yields", MARKET_DATA_YIELD_FIELD);
        REGISTRY.get(FieldResponse.class).put("trades_yields", TRADE_YIELD_FIELD);
        REGISTRY.get(FieldResponse.class).put("history_yields", HISTORY_YIELD_FIELD);
        REGISTRY.get(FieldResponse.class).put("secstats", SECURITY_STATISTIC_FIELD);
    }

    public static EntityType<? extends BasicEntity<?>, ? extends Enum<?>> getEntityType(Class<? extends Response> clazz, String type)
    {
        EntityType<? extends BasicEntity<?>, ? extends Enum<?>> entityType = REGISTRY.get(clazz).get(type);
        if (entityType == null)
            entityType = REGISTRY.get(Response.class).get(type);
        if (entityType == null)
            log.warn("Unknown entity type '{}'", type);
        return entityType;
    }

    public T createInstance(JsonElement jsonElement, JsonArray columns, JsonObject metadata)
    {
        return this.processor.processJsonElement(jsonElement, columns, metadata);
    }
}
