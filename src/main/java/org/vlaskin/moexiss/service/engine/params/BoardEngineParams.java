package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEngineParams extends BoardsEngineParams
{
    private final String board;

    public BoardEngineParams(String engine, String market, String board)
    {
        super(engine, market);
        this.board = board;
    }
}
