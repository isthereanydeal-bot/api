package isthereanydeal.app.games.domain;

import isthereanydeal.app.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "game_prices")
public class GameDeal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false)
    private Game game;

    @Column(length = 2, nullable = false)
    private String locale;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private Integer regularPrice;

    @Column(nullable = false)
    private Integer lowestPrice;

    @Column(nullable = false)
    private Integer currentPrice;

    @Column(nullable = false)
    private Integer cut;

    private ZonedDateTime endTime;

}
