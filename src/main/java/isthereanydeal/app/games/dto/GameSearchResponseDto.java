package isthereanydeal.app.games.dto;

import isthereanydeal.app.games.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GameSearchResponseDto {
    private final String id;
    private final String title;
    private final String slug;
    private final boolean mature;
    private final String type;
    private final String banner;

    public static GameSearchResponseDto from(String id, String title, String slug, boolean mature, String type, String banner) {
        return GameSearchResponseDto.builder()
                .id(id)
                .title(title)
                .slug(slug)
                .mature(mature)
                .type(type)
                .banner(banner)
                .build();
    }
}
