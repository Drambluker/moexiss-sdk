package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;

/** Параметры запроса режимов торгов выбранного рынка. */
@Getter
@Setter
public class BoardsEngineParams
{
    protected final String engine;
    protected final String market;

    protected Language language = Language.RU;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     */
    public BoardsEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }
}
