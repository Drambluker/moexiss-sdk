package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class TickerInfoResponse
{
    private List<TickerResponse> tickers;
    private List<CursorResponse> cursors;
}
