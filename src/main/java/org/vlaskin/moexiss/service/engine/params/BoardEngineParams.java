package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

/** Параметры запроса отдельного режима торгов. */
@Getter
@Setter
public class BoardEngineParams extends BoardsEngineParams
{
    private final String board;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     * @param board код режима торгов
     */
    public BoardEngineParams(String engine, String market, String board)
    {
        super(engine, market);
        this.board = board;
    }
}
