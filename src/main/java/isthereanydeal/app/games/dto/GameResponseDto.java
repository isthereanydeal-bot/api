package isthereanydeal.app.games.dto;

import isthereanydeal.app.games.domain.Game;
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
    private String expiry; // ISO 또는 Relative String

    public static GameResponseDto from(Game game) {
        return GameResponseDto.builder()
                .title(game.getName())
                .cut(game.getCut())
                .price(game.getCurrentPrice())
                .regular(game.getRegularPrice())
                .historyLow(game.getLowestPrice())
                .url(game.getUrl())
                .expiry(String.valueOf(game.getEndTime()))
                .build();
    }
}