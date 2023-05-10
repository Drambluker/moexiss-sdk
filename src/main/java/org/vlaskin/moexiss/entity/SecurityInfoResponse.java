package org.vlaskin.moexiss.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SecurityInfoResponse
{
    private final List<DescriptionResponse> descriptions;
    private final List<BoardResponse> boards;
}
