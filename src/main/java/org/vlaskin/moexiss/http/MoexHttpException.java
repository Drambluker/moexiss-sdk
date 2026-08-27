package org.vlaskin.moexiss.http;

import java.io.IOException;

/**
 * Ошибка HTTP-ответа MOEX ISS с доступным для программной обработки кодом состояния.
 */
public final class MoexHttpException extends IOException
{
    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String requestTarget;

    MoexHttpException(int statusCode, String requestTarget, Throwable cause)
    {
        super("MOEX ISS request failed with HTTP " + statusCode + " for " + requestTarget, cause);
        this.statusCode = statusCode;
        this.requestTarget = requestTarget;
    }

    /**
     * Возвращает HTTP-код ответа.
     *
     * @return HTTP-код состояния
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Возвращает адрес запроса без пользовательских данных, query-параметров и фрагмента.
     *
     * @return безопасное представление адреса запроса
     */
    public String getRequestTarget()
    {
        return requestTarget;
    }
}
