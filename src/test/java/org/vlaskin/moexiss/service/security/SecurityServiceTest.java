package org.vlaskin.moexiss.service.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vlaskin.moexiss.MoexClient;
import org.vlaskin.moexiss.service.TestUtils;
import org.vlaskin.moexiss.service.security.params.IndicesSecurityParams;
import org.vlaskin.moexiss.service.security.params.InfoSecurityParams;
import org.vlaskin.moexiss.service.security.params.ListSecurityParams;

import java.io.IOException;

// TODO
@Slf4j
class SecurityServiceTest
{
    private static final MoexClient client = new MoexClient();
    private static final SecurityService service = client.getSecurities();

    private static final ListSecurityParams listParams = new ListSecurityParams();
    private static final InfoSecurityParams infoParams = new InfoSecurityParams(TestUtils.SECURITY);
    private static final IndicesSecurityParams indicesParams = new IndicesSecurityParams(TestUtils.SECURITY);

    @Test
    void getList() throws IOException
    {
        log.info(service.getList(listParams).toString());
    }

    @Test
    void getInfo() throws IOException
    {
        log.info(service.getInfo(infoParams).toString());
    }

    @Test
    void getDescriptionsInfo() throws IOException
    {
        log.info(service.getDescriptionsInfo(infoParams).toString());
    }

    @Test
    void getBoardsInfo() throws IOException
    {
        log.info(service.getBoardsInfo(infoParams).toString());
    }

    @Test
    void getIndices() throws IOException
    {
        log.info(service.getIndices(indicesParams).toString());
    }
}
