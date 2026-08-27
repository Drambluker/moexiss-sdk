package org.vlaskin.moexiss;

import lombok.Getter;
import org.vlaskin.moexiss.http.ApacheMoexHttpTransport;
import org.vlaskin.moexiss.http.MoexHttpTransport;
import org.vlaskin.moexiss.service.BaseService;
import org.vlaskin.moexiss.service.dictionary.DictionaryService;
import org.vlaskin.moexiss.service.engine.EngineService;
import org.vlaskin.moexiss.service.security.SecurityService;
import org.vlaskin.moexiss.service.statistic.StatisticService;

/**
 * Точка входа в SDK, объединяющая сервисы MOEX ISS.
 *
 * <p>Для обычной работы достаточно конструктора без аргументов. Конструкторы с адресом и
 * транспортом предназначены для прокси, тестовых стендов и локальных HTTP mock-серверов.
 */
@Getter
public final class MoexClient
{
    private final SecurityService securities;
    private final DictionaryService dictionaries;
    private final EngineService engines;
    private final StatisticService statistics;

    /** Создаёт клиент для официального адреса MOEX ISS со стандартным HTTP-транспортом. */
    public MoexClient()
    {
        this(BaseService.DEFAULT_BASE_URL);
    }

    /**
     * Создаёт клиент со стандартным HTTP-транспортом.
     *
     * @param baseUrl базовый адрес сервера без пути {@code /iss}
     */
    public MoexClient(String baseUrl)
    {
        this(baseUrl, new ApacheMoexHttpTransport());
    }

    /**
     * Создаёт клиент с заданным транспортом.
     *
     * @param baseUrl базовый адрес сервера без пути {@code /iss}
     * @param httpTransport транспорт, выполняющий GET-запросы
     */
    public MoexClient(String baseUrl, MoexHttpTransport httpTransport)
    {
        securities = new SecurityService(baseUrl, httpTransport);
        dictionaries = new DictionaryService(baseUrl, httpTransport);
        engines = new EngineService(baseUrl, httpTransport);
        statistics = new StatisticService(baseUrl, httpTransport);
    }
}
