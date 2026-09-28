package org.vlaskin.moexiss.service.history;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Дробление или консолидация из справочника ISS.
 * @param securityCode код бумаги
 * @param date дата события
 * @param before число бумаг до события
 * @param after соответствующее число бумаг после события
 */
public record SecurityCorporateAction(String securityCode, LocalDate date, BigDecimal before, BigDecimal after) {}
