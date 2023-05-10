package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SecurityInfo
{
    private final List<Description> descriptions;
    private final List<Board> boards;
}
