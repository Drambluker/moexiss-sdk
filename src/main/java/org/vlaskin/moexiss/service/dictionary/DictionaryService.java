package org.vlaskin.moexiss.service.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.vlaskin.moexiss.entity.BoardGroupResponse;
import org.vlaskin.moexiss.entity.BoardResponse;
import org.vlaskin.moexiss.entity.DictionaryResponse;
import org.vlaskin.moexiss.entity.DurationResponse;
import org.vlaskin.moexiss.entity.EngineResponse;
import org.vlaskin.moexiss.entity.MarketResponse;
import org.vlaskin.moexiss.entity.SecurityCollectionResponse;
import org.vlaskin.moexiss.entity.SecurityGroupResponse;
import org.vlaskin.moexiss.entity.SecurityTypeResponse;
import org.vlaskin.moexiss.entity.base.EntityType;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.response.Response;
import org.vlaskin.moexiss.response.ResponseUtils;
import org.vlaskin.moexiss.service.BaseService;

import java.io.IOException;
import java.util.List;

/** Сервис справочников торговых систем, рынков, режимов торгов и типов инструментов. */
@Slf4j
public final class DictionaryService extends BaseService
{
    /** Создаёт сервис для официального адреса MOEX ISS. */
    public DictionaryService()
    {
        super();
    }

    /**
     * Создаёт сервис для заданного адреса и транспорта.
     *
     * @param baseUrl базовый адрес сервера
     * @param httpTransport HTTP-транспорт
     */
    public DictionaryService(String baseUrl, MoexHttpTransport httpTransport)
    {
        super(baseUrl, httpTransport);
    }

    /**
     * Возвращает все справочники MOEX ISS одним запросом.
     */
    public DictionaryResponse getAll() throws IOException
    {
        Response response = getResponse();
        return DictionaryResponse.builder()
                .engines(ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, response))
                .markets(ResponseUtils.convertTo(EntityType.MARKET, response))
                .boards(ResponseUtils.convertTo(EntityType.BOARD, response))
                .boardGroups(ResponseUtils.convertTo(EntityType.BOARD_GROUP, response))
                .durations(ResponseUtils.convertTo(EntityType.DURATION, response))
                .securityTypes(ResponseUtils.convertTo(EntityType.SECURITY_TYPE, response))
                .securityGroups(ResponseUtils.convertTo(EntityType.SECURITY_GROUP, response))
                .securityCollections(ResponseUtils.convertTo(EntityType.SECURITY_COLLECTION, response))
                .build();
    }

    /**
     * Возвращает торговые системы.
     */
    public List<EngineResponse> getEngines() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.TRADING_SYSTEM, getResponse("engines"));
    }

    /**
     * Возвращает рынки торговых систем.
     */
    public List<MarketResponse> getMarkets() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.MARKET, getResponse("markets"));
    }

    /**
     * Возвращает режимы торгов.
     */
    public List<BoardResponse> getBoards() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.BOARD, getResponse("boards"));
    }

    /**
     * Возвращает группы режимов торгов.
     */
    public List<BoardGroupResponse> getBoardGroups() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.BOARD_GROUP, getResponse("boardgroups"));
    }

    /**
     * Возвращает доступные интервалы свечей.
     */
    public List<DurationResponse> getDurations() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.DURATION, getResponse("durations"));
    }

    /**
     * Возвращает типы ценных бумаг.
     */
    public List<SecurityTypeResponse> getSecurityTypes() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.SECURITY_TYPE, getResponse("securitytypes"));
    }

    /**
     * Возвращает группы ценных бумаг.
     */
    public List<SecurityGroupResponse> getSecurityGroups() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.SECURITY_GROUP, getResponse("securitygroups"));
    }

    /**
     * Возвращает коллекции ценных бумаг.
     */
    public List<SecurityCollectionResponse> getSecurityCollections() throws IOException
    {
        return ResponseUtils.convertTo(EntityType.SECURITY_COLLECTION,
                getResponse("securitycollections"));
    }

    private Response getResponse(String... only) throws IOException
    {
        StringBuilder requestBuilder = new StringBuilder(baseUrl).append("/iss/index.json");
        pasteBasicRequestParams(requestBuilder, only);

        log.debug("Request: {}", requestBuilder);
        return getAndParseResponse(requestBuilder, Response.class);
    }
}
