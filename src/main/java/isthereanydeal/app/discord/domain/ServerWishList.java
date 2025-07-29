package isthereanydeal.app.discord.domain;

import isthereanydeal.app.common.domain.BaseTimeEntity;
import isthereanydeal.app.games.domain.Game;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "server_wishlist")
public class ServerWishList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "server_id", insertable = false, updatable = false, nullable = false)
    private BigInteger serverId;

    @Column(name = "game_id", insertable = false, updatable = false, nullable = false)
    private String gameId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id", referencedColumnName = "id")
    private Server server;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

}
