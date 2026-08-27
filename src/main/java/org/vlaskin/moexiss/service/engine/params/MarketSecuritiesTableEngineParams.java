package org.vlaskin.moexiss.service.engine.params;

import lombok.Getter;
import lombok.Setter;
import org.vlaskin.moexiss.params.Language;
import org.vlaskin.moexiss.params.Order;

import java.util.Collection;
import java.util.List;

/**
 * Параметры таблицы инструментов рынка.
 *
 * <p>Поддерживает фильтры по активам, инструментам, типам и коллекции, а также сортировку и
 * выбор данных основной, ближайшей или предыдущей сессии. Коллекции копируются при установке.
 */
@Getter
@Setter
public class MarketSecuritiesTableEngineParams
{
    protected final String engine;
    protected final String market;

    protected Language language = Language.RU;
    protected int first = 0;
    protected String sortColumn;
    protected Order sortOrder = Order.ASC;
    protected boolean leaders = false;
    protected boolean nearest = false;
    protected boolean previousSession = false;
    protected String primaryBoard;
    protected List<String> assets;
    protected String index;
    protected List<String> securities;
    protected List<String> securityTypes;
    protected String securityCollection;

    /**
     * @param engine код торговой системы
     * @param market код рынка
     */
    public MarketSecuritiesTableEngineParams(String engine, String market)
    {
        this.engine = engine;
        this.market = market;
    }

    /** @return неизменяемый список кодов классов активов либо {@code null} */
    public List<String> getAssets()
    {
        return copyOf(assets);
    }

    /** @param assets коды классов активов либо {@code null} */
    public void setAssets(Collection<String> assets)
    {
        this.assets = copyOf(assets);
    }

    /** @return неизменяемый список кодов инструментов либо {@code null} */
    public List<String> getSecurities()
    {
        return copyOf(securities);
    }

    /** @param securities коды инструментов либо {@code null} */
    public void setSecurities(Collection<String> securities)
    {
        this.securities = copyOf(securities);
    }

    /** @return неизменяемый список типов инструментов либо {@code null} */
    public List<String> getSecurityTypes()
    {
        return copyOf(securityTypes);
    }

    /** @param securityTypes типы инструментов либо {@code null} */
    public void setSecurityTypes(Collection<String> securityTypes)
    {
        this.securityTypes = copyOf(securityTypes);
    }

    private static <T> List<T> copyOf(Collection<T> values)
    {
        return values == null ? null : List.copyOf(values);
    }
}
