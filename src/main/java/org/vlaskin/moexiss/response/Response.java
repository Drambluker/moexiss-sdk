package org.vlaskin.moexiss.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.vlaskin.moexiss.entity.base.BasicEntity;
import org.vlaskin.moexiss.entity.base.EntityType;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Response
{
    protected final Map<EntityType<? extends BasicEntity<?>, ? extends Enum<?>>, List<? extends BasicEntity<?>>> data;
}
