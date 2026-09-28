package org.vlaskin.moexiss.service.history;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Денежная выплата; неподтверждённый источник не должен создавать такие записи.
 * @param securityCode код бумаги
 * @param date дата выплаты
 * @param currencyCode валюта
 * @param amount сумма на одну бумагу
 */
public record SecurityDistribution(String securityCode, LocalDate date, String currencyCode, BigDecimal amount) {}
