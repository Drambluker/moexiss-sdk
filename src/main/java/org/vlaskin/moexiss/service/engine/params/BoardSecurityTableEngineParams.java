package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;

/** Параметры таблицы одного инструмента на выбранном режиме торгов. */
@Getter
@Setter
public class BoardSecurityTableEngineParams extends BoardSecuritiesTableEngineParams
{
    private final String security;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     * @param board код режима торгов
     * @param security код инструмента
     */
    public BoardSecurityTableEngineParams(String engine, String market, String board, String security)
    {
        super(engine, market, board);
        this.security = security;
    }
}
