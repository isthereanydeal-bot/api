package isthereanydeal.app.games.domain;

import isthereanydeal.app.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseTimeEntity{
    @Id
    private String id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String url;

    private String banner;

    @Lob
    private String description; // BLOB 타입으로 변경, 필요시 적절한 변환 로직 추가

    // 아마 사용하지는 않겠지만 해당 games 테이블에서 게임이 지워지면 이게 인기게임에 있으면 안되니 연결을 해줍니다.
    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private PopularDeals popularDeals;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameDeal> prices = new ArrayList<>();

}
