package isthereanydeal.app.games.dto;

import isthereanydeal.app.games.domain.Game;
import isthereanydeal.app.games.domain.GameDeal;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class GameResponseDto {
    private String title;
    private int cut;
    private int price;
    private int regular;
    private int historyLow;
    private String url;
    private String storeName;
    private String currency;
    private String expiry; // ISO 또는 Relative String

    public static GameResponseDto from(Game game, GameDeal deal) {
        return GameResponseDto.builder()
                .title(game.getName())
                .cut(deal.getCut())
                .price(deal.getCurrentPrice())
                .regular(deal.getRegularPrice())
                .historyLow(deal.getLowestPrice())
                .storeName(deal.getStoreName())
                .currency(deal.getCurrency() != null ? deal.getCurrency() : "USD")
                .url(game.getUrl())
                .expiry(deal.getEndTime() != null ? deal.getEndTime().toString() : null)
                .build();
    }
}