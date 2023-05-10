package org.vlaskin.moexiss.entity.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.entity.Board;
import org.vlaskin.moexiss.entity.BoardGroup;
import org.vlaskin.moexiss.entity.Cursor;
import org.vlaskin.moexiss.entity.DailyTableRecord;
import org.vlaskin.moexiss.entity.DataVersion;
import org.vlaskin.moexiss.entity.Description;
import org.vlaskin.moexiss.entity.Engine;
import org.vlaskin.moexiss.entity.Index;
import org.vlaskin.moexiss.entity.IndexAnalyticsData;
import org.vlaskin.moexiss.entity.IndexAnalyticsDates;
import org.vlaskin.moexiss.entity.Market;
import org.vlaskin.moexiss.entity.MarketData;
import org.vlaskin.moexiss.entity.MarketDataYield;
import org.vlaskin.moexiss.entity.Security;
import org.vlaskin.moexiss.entity.TableField;
import org.vlaskin.moexiss.entity.Ticker;
import org.vlaskin.moexiss.entity.TimeTableRecord;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.field.FieldResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public record EntityType<T>(AbstractProcessor<T> processor)
{
    private static final Map<Class<? extends Response>, Map<String, EntityType<?>>> REGISTRY = new HashMap<>();

    public static final EntityType<Cursor> CURSOR = new EntityType<>(new Cursor.Processor());
    public static final EntityType<DataVersion> DATA_VERSION = new EntityType<>(new DataVersion.Processor());
    public static final EntityType<TableField> TABLE_FIELD = new EntityType<>(new TableField.Processor());
    public static final EntityType<TableField> SECURITY_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> MARKET_DATA_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> TRADE_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> ORDER_BOOK_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> HISTORY_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> TRADE_HISTORY_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> MARKET_DATA_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> TRADE_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> HISTORY_YIELD_FIELD = TABLE_FIELD;
    public static final EntityType<TableField> SECURITY_STATISTIC_FIELD = TABLE_FIELD;
    public static final EntityType<Security> SECURITY = new EntityType<>(new Security.Processor());
    public static final EntityType<Description> DESCRIPTION = new EntityType<>(new Description.Processor());
    public static final EntityType<Board> BOARD = new EntityType<>(new Board.Processor());
    public static final EntityType<BoardGroup> BOARD_GROUP = new EntityType<>(new BoardGroup.Processor());
    public static final EntityType<Index> INDEX = new EntityType<>(new Index.Processor());
    public static final EntityType<Engine> TRADING_SYSTEM = new EntityType<>(new Engine.Processor());
    public static final EntityType<TimeTableRecord> TIME_TABLE = new EntityType<>(new TimeTableRecord.Processor());
    public static final EntityType<DailyTableRecord> DAILY_TABLE = new EntityType<>(new DailyTableRecord.Processor());
    public static final EntityType<Market> MARKET = new EntityType<>(new Market.Processor());
    public static final EntityType<MarketData> MARKET_DATA = new EntityType<>(new MarketData.Processor());
    public static final EntityType<MarketDataYield> MARKET_DATA_YIELD = new EntityType<>(new MarketDataYield.Processor());
    public static final EntityType<IndexAnalyticsData> INDEX_ANALYTICS = new EntityType<>(new IndexAnalyticsData.Processor());
    public static final EntityType<IndexAnalyticsDates> INDEX_ANALYTICS_DATES = new EntityType<>(new IndexAnalyticsDates.Processor());
    public static final EntityType<Ticker> TICKER = new EntityType<>(new Ticker.Processor());

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

    public static EntityType<?> getEntityType(Class<? extends Response> clazz, String type)
    {
        EntityType<?> entityType = REGISTRY.get(clazz).get(type);
        if (entityType == null)
            entityType = REGISTRY.get(Response.class).get(type);
        if (entityType == null)
            log.warn("Unknown entity type '{}'", type);
        return entityType;
    }

    public T createInstance(JsonElement jsonElement, JsonArray columns)
    {
        return this.processor.processJsonElement(jsonElement, columns);
    }
}
