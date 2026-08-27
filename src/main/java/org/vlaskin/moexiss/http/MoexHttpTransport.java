package org.vlaskin.moexiss.http;

import java.io.IOException;

/** Транспорт для выполнения HTTP GET-запросов к MOEX ISS. */
@FunctionalInterface
public interface MoexHttpTransport
{
    /**
     * Выполняет запрос и возвращает тело ответа в виде строки.
     *
     * @param url абсолютный URL запроса
     * @return тело успешного HTTP-ответа
     * @throws IOException если запрос не выполнен или сервер вернул ошибку
     */
    String get(String url) throws IOException;
}
