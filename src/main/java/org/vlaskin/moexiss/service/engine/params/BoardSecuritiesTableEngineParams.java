package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSecuritiesTableEngineParams extends MarketSecuritiesTableEngineParams
{
    protected final String board;

    public BoardSecuritiesTableEngineParams(String engine, String market, String board)
    {
        super(engine, market);
        this.board = board;
    }
}
