package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

/** Параметры таблицы инструментов выбранного режима торгов. */
@Getter
@Setter
public class BoardSecuritiesTableEngineParams extends MarketSecuritiesTableEngineParams
{
    protected final String board;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     * @param board код режима торгов
     */
    public BoardSecuritiesTableEngineParams(String engine, String market, String board)
    {
        super(engine, market);
        this.board = board;
    }
}
