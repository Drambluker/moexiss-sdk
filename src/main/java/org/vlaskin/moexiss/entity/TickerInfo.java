package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class TickerInfo
{
    private List<Ticker> tickers;
    private List<Cursor> cursors;
}
