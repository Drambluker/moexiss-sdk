package org.vlaskin.moexiss.service.history;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Исторические итоги одной торговой сессии; значения не приводятся к рублям.
 * @param securityCode код бумаги
 * @param boardCode режим торгов
 * @param date дата торгов
 * @param close цена закрытия, null при отсутствии цены; для облигации проценты номинала
 * @param currencyCode валюта котировки, включая коды ISS SUR/RUR
 * @param faceValue исторический номинал облигации, null для акции
 * @param faceCurrencyCode валюта номинала, null для акции
 * @param accruedInterest исторический НКД на одну облигацию, null для акции
 * @param tradingSession сессия; не объединяется автоматически с другими
 */
public record SecurityHistoryRow(String securityCode, String boardCode, LocalDate date, BigDecimal close,
                                 String currencyCode, BigDecimal faceValue, String faceCurrencyCode,
                                 BigDecimal accruedInterest, String tradingSession) {}
