package org.vlaskin.moexiss.service.security;

import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.FixtureTransport;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.entity.DescriptionResponse;
import org.vlaskin.moexiss.entity.IndexResponse;
import org.vlaskin.moexiss.entity.SecurityInfoResponse;
import org.vlaskin.moexiss.entity.SecurityResponse;
import org.vlaskin.moexiss.params.GroupBy;
import org.vlaskin.moexiss.service.security.params.IndicesSecurityParams;
import org.vlaskin.moexiss.service.security.params.InfoSecurityParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityServiceTest
{
    @Test
    void deserializesSecurityDescriptionAndBoards() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("security-info.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);
        InfoSecurityParams params = new InfoSecurityParams("SBER /+");
        params.setStartIndex(20);

        SecurityInfoResponse info = service.getInfo(params);

        assertEquals("SBER", info.getDescriptions().getFirst()
                .get(DescriptionResponse.Fields.VALUE, String.class));
        assertEquals("TQBR", info.getBoards().getFirst().getCode());
        assertEquals(1, service.getDescriptionsInfo(params).size());
        assertEquals(1, service.getBoardsInfo(params).size());
        assertEquals("https://fixture.test/iss/securities/SBER%20%2F%2B.json"
                        + "?iss.meta=on&iss.only=boards&lang=ru&boards.start=20",
                transport.getRequestedUrl());
    }

    @Test
    void deserializesActualSecurityIndices() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("security-indices.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);
        IndicesSecurityParams params = new IndicesSecurityParams("SBER");
        params.setOnlyActual(true);

        List<IndexResponse> indices = service.getIndices(params);

        assertEquals(1, indices.size());
        assertEquals("EPSI", indices.getFirst()
                .get(IndexResponse.Fields.SECURITY_CODE, String.class));
        assertEquals(LocalDate.of(2011, 11, 21), indices.getFirst().getFrom());
        assertEquals("https://fixture.test/iss/securities/SBER/indices.json"
                        + "?iss.meta=on&lang=ru&only_actual=1",
                transport.getRequestedUrl());
    }

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
        assertEquals("SBER", securities.getFirst().getCode());
        assertTrue(securities.getFirst().getTraded());
        assertEquals(1000.0, securities.getFirst().getFaceValueOnSettlementDate());
        assertEquals(LocalDate.of(2026, 9, 1), securities.getFirst().getCallOptionDate());
        assertEquals("Fixed", securities.getFirst().getBondType());
    }

    @Test
    void rejectsGroupByFilterWithoutGrouping()
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);
        ListSecurityParams params = new ListSecurityParams();
        params.setGroupByFilter("common_share");

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> service.getList(params));

        assertEquals("Parameter groupBy is required when groupByFilter is used",
                exception.getMessage());
        assertNull(transport.getRequestedUrl());
    }

    @Test
    void encodesTextFilters() throws IOException
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);
        ListSecurityParams params = new ListSecurityParams();
        params.setQuery("Сбер +&/");
        params.setEngine("stock&future");
        params.setMarket("shares +");

        service.getList(params);

        assertEquals("https://fixture.test/iss/securities.json?iss.meta=on&lang=ru&start=0&limit=100"
                + "&q=%D0%A1%D0%B1%D0%B5%D1%80+%2B%26%2F&engine=stock%26future&market=shares+%2B",
                transport.getRequestedUrl());
    }

    @Test
    void rejectsInvalidPaginationBeforeRequest()
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);
        ListSecurityParams params = new ListSecurityParams();
        params.setPageIndex(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getList(params));

        assertEquals("Parameter pageIndex must not be negative",
                exception.getMessage());
        assertNull(transport.getRequestedUrl());
    }

    @Test
    void rejectsMissingParamsBeforeRequest()
    {
        FixtureTransport transport = new FixtureTransport("securities.json");
        SecurityService service = new SecurityService("https://fixture.test", transport);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> service.getList(null));

        assertEquals("Request parameters must not be null", exception.getMessage());
        assertNull(transport.getRequestedUrl());
    }
}
