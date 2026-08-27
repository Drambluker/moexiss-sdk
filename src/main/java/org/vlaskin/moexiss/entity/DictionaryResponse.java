package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Составной ответ со всеми справочниками MOEX ISS.
 */

@Getter
@ToString
public final class DictionaryResponse
{
    private final List<EngineResponse> engines;
    private final List<MarketResponse> markets;
    private final List<BoardResponse> boards;
    private final List<BoardGroupResponse> boardGroups;
    private final List<DurationResponse> durations;
    private final List<SecurityTypeResponse> securityTypes;
    private final List<SecurityGroupResponse> securityGroups;
    private final List<SecurityCollectionResponse> securityCollections;

    /**
     * @param engines торговые системы
     * @param markets рынки
     * @param boards режимы торгов
     * @param boardGroups группы режимов торгов
     * @param durations интервалы свечей
     * @param securityTypes типы инструментов
     * @param securityGroups группы инструментов
     * @param securityCollections коллекции инструментов
     */
    @Builder
    public DictionaryResponse(List<EngineResponse> engines,
                              List<MarketResponse> markets,
                              List<BoardResponse> boards,
                              List<BoardGroupResponse> boardGroups,
                              List<DurationResponse> durations,
                              List<SecurityTypeResponse> securityTypes,
                              List<SecurityGroupResponse> securityGroups,
                              List<SecurityCollectionResponse> securityCollections)
    {
        this.engines = ResponseLists.immutableCopy(engines);
        this.markets = ResponseLists.immutableCopy(markets);
        this.boards = ResponseLists.immutableCopy(boards);
        this.boardGroups = ResponseLists.immutableCopy(boardGroups);
        this.durations = ResponseLists.immutableCopy(durations);
        this.securityTypes = ResponseLists.immutableCopy(securityTypes);
        this.securityGroups = ResponseLists.immutableCopy(securityGroups);
        this.securityCollections = ResponseLists.immutableCopy(securityCollections);
    }
}
