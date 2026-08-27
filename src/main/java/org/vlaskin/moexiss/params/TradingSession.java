package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

/** Торговая сессия, используемая статистическими методами MOEX ISS. */
@Slf4j
@AllArgsConstructor
public enum TradingSession
{
    /** Основная сессия. */
    MAIN(1),
    /** Вечерняя сессия. */
    EVENING(2),
    /** Совокупные данные всех сессий. */
    TOTAL(3);

    private final int code;

    /**
     * Находит сессию по числовому коду MOEX ISS.
     *
     * @param code код сессии
     * @return сессия либо {@code null}, если код неизвестен
     */
    public static TradingSession getTradingSession(int code)
    {
        TradingSession result = Arrays.stream(TradingSession.values())
                .filter(tradingSession -> tradingSession.code == code)
                .findFirst().orElse(null);

        if (result == null)
            log.warn("Unknown trading session code '{}'", code);

        return result;
    }

    @Override
    public String toString()
    {
        return Objects.toString(code);
    }
}
