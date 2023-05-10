package org.vlaskin.moexiss.params;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public enum TradingSession
{
    MAIN(1), EVENING(2), TOTAL(3);

    private final int code;

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
