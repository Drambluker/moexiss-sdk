package org.vlaskin.moexiss.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.vlaskin.moexiss.entity.base.AbstractProcessor;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Board
{
    private final Integer id;
    private final Integer boardGroupId;
    private final String boardId;
    private final String title;
    private final String secId;
    private final Integer marketId;
    private final String market;
    private final Integer engineId;
    private final String engine;
    private final Boolean traded;
    private final Integer decimals;
    private final LocalDate historyFrom;
    private final LocalDate historyTill;
    private final LocalDate listedFrom;
    private final LocalDate listedTill;
    private final Boolean primary;
    private final String currencyId;

    public static class Processor extends AbstractProcessor<Board>
    {
        @Override
        public Board processJsonElement(JsonElement jsonElement, JsonArray columns)
        {
            BoardBuilder builder = new BoardBuilder();
            process(builder, jsonElement, columns);
            return builder.build();
        }

        @Override
        protected void processValue(Object builder, JsonElement value, String name) throws UnknownAttributeException
        {
            BoardBuilder boardBuilder = (BoardBuilder) builder;
            switch (name)
            {
                case "id" -> boardBuilder.id(value.getAsInt());
                case "board_group_id" -> boardBuilder.boardGroupId(value.getAsInt());
                case "boardid" -> boardBuilder.boardId(value.getAsString());
                case "title" -> boardBuilder.title(value.getAsString());
                case "secid" -> boardBuilder.secId(value.getAsString());
                case "market_id" -> boardBuilder.marketId(value.getAsInt());
                case "market" -> boardBuilder.market(value.getAsString());
                case "engine_id" -> boardBuilder.engineId(value.getAsInt());
                case "engine" -> boardBuilder.engine(value.getAsString());
                case "is_traded" -> boardBuilder.traded(value.getAsInt() == 1);
                case "decimals" -> boardBuilder.decimals(value.getAsInt());
                case "history_from" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        boardBuilder.historyFrom(LocalDate.parse(value.getAsString()));
                }
                case "history_till" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        boardBuilder.historyTill(LocalDate.parse(value.getAsString()));
                }
                case "listed_from" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        boardBuilder.listedFrom(LocalDate.parse(value.getAsString()));
                }
                case "listed_till" -> {
                    if (!"0000-00-00".equals(value.getAsString()))
                        boardBuilder.listedTill(LocalDate.parse(value.getAsString()));
                }
                case "is_primary" -> boardBuilder.primary(value.getAsInt() == 1);
                case "currencyid" -> boardBuilder.currencyId(value.getAsString());
                default -> throw new UnknownAttributeException(getClass(), name);
            }
        }
    }
}
