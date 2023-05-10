package org.vlaskin.moexiss;

import lombok.Getter;
import org.vlaskin.moexiss.service.dictionary.DictionaryService;
import org.vlaskin.moexiss.service.engine.EngineService;
import org.vlaskin.moexiss.service.security.SecurityService;
import org.vlaskin.moexiss.service.statistic.StatisticService;

@Getter
public class MoexClient
{
    private final SecurityService securities = new SecurityService();
    private final DictionaryService dictionaries = new DictionaryService();
    private final EngineService engines = new EngineService();
    private final StatisticService statistics = new StatisticService();
}
