package org.vlaskin.moexiss;

import lombok.Getter;
import org.vlaskin.moexiss.http.ApacheMoexHttpTransport;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.dictionary.DictionaryService;
import org.vlaskin.moexiss.service.engine.EngineService;
import org.vlaskin.moexiss.service.security.SecurityService;
import org.vlaskin.moexiss.service.statistic.StatisticService;

@Getter
public class MoexClient
{
    private final SecurityService securities;
    private final DictionaryService dictionaries;
    private final EngineService engines;
    private final StatisticService statistics;

    public MoexClient()
    {
        this(BaseService.DEFAULT_BASE_URL);
    }

    public MoexClient(String baseUrl)
    {
        this(baseUrl, new ApacheMoexHttpTransport());
    }

    public MoexClient(String baseUrl, MoexHttpTransport httpTransport)
    {
        securities = new SecurityService(baseUrl, httpTransport);
        dictionaries = new DictionaryService(baseUrl, httpTransport);
        engines = new EngineService(baseUrl, httpTransport);
        statistics = new StatisticService(baseUrl, httpTransport);
    }
}
