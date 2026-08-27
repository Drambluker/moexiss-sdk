package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Составной ответ со спецификацией инструмента и доступными режимами торгов.
 */

@Getter
@ToString
public final class SecurityInfoResponse
{
    private final List<DescriptionResponse> descriptions;
    private final List<BoardResponse> boards;

    /**
     * @param descriptions спецификация инструмента
     * @param boards режимы торгов инструмента
     */
    @Builder
    public SecurityInfoResponse(List<DescriptionResponse> descriptions,
                                List<BoardResponse> boards)
    {
        this.descriptions = ResponseLists.immutableCopy(descriptions);
        this.boards = ResponseLists.immutableCopy(boards);
    }
}
