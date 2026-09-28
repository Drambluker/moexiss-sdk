package org.vlaskin.moexiss.service.history;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Историческое значение индекса, без предположений о методике выплат.
 * @param indexCode код индекса
 * @param boardCode режим расчёта
 * @param date дата торгов
 * @param close значение закрытия, null при отсутствии
 * @param currencyCode валюта значения
 * @param tradingSession сессия
 */
public record IndexHistoryRow(String indexCode, String boardCode, LocalDate date, BigDecimal close,
                              String currencyCode, String tradingSession) {}
