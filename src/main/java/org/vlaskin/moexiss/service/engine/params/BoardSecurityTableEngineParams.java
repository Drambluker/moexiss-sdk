package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSecurityTableEngineParams extends BoardSecuritiesTableEngineParams
{
    private final String security;

    public BoardSecurityTableEngineParams(String engine, String market, String board, String security)
    {
        super(engine, market, board);
        this.security = security;
    }
}
