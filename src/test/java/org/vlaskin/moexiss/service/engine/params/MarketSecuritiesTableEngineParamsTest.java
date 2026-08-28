package org.vlaskin.moexiss.service.engine.params;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarketSecuritiesTableEngineParamsTest
{
    @Test
    void protectsCollectionParametersFromModification()
    {
        MarketSecuritiesTableEngineParams params =
                new MarketSecuritiesTableEngineParams("stock", "shares");
        List<String> securities = new ArrayList<>(List.of("SBER"));

        params.setSecurities(securities);
        securities.add("GAZP");

        assertEquals(List.of("SBER"), params.getSecurities());
        assertThrows(UnsupportedOperationException.class,
                () -> params.getSecurities().add("LKOH"));
    }

    @Test
    void acceptsMissingCollectionParameters()
    {
        MarketSecuritiesTableEngineParams params =
                new MarketSecuritiesTableEngineParams("stock", "shares");

        params.setAssets(null);
        params.setSecurities(null);
        params.setSecurityTypes(null);

        assertNull(params.getAssets());
        assertNull(params.getSecurities());
        assertNull(params.getSecurityTypes());
    }
}
