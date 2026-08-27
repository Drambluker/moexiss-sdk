package org.vlaskin.moexiss.service.security;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.params.GroupBy;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityServiceTest
{
    @Test
    void buildsFilterRequestAndDeserializesSecurity() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        MoexClient client = new MoexClient("https://fixture.test", transport);
        ListSecurityParams params = new ListSecurityParams();
        params.setPageIndex(2);
        params.setLimit(50);
        params.setQuery("Sber bank");
        params.setEngine("stock");
        params.setMarket("shares");
        params.setTrading(true);
        params.setGroupBy(GroupBy.TYPE);
        params.setGroupByFilter("common_share");

        List<SecurityResponse> securities = client.getSecurities().getList(params);

        assertEquals("https://fixture.test/iss/securities.json?iss.meta=on&lang=ru&start=100&limit=50"
                + "&q=Sber+bank&engine=stock&market=shares&is_trading=1&group_by=type&group_by_filter=common_share",
                transport.getRequestedUrl());
        assertEquals(1, securities.size());
        assertEquals("SBER", securities.getFirst().getStringFields().get(SecurityResponse.Fields.CODE));
        assertTrue(securities.getFirst().getBooleanFields().get(SecurityResponse.Fields.IS_TRADED));
    }
}
