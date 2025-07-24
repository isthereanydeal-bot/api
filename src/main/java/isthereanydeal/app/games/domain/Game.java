package isthereanydeal.app.games.domain;

import isthereanydeal.app.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

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

    @Column(nullable = false)
    private Integer regularPrice;

    private Integer cut;

    @Column(nullable = false)
    private Integer lowestPrice;

    @Column(nullable = false)
    private Integer currentPrice;

    private ZonedDateTime endTime;

    private String url;

    private String imageUrl;

    public void updateFrom(Game other) {
        this.cut = other.cut;
        this.currentPrice = other.currentPrice;
        this.regularPrice = other.regularPrice;
        this.lowestPrice = other.lowestPrice;
        this.url = other.url;
        this.endTime = other.endTime;
        this.imageUrl = other.imageUrl;
    }

}
