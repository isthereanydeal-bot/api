package isthereanydeal.app.discord.domain;

import isthereanydeal.app.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "servers")
public class Server extends  BaseTimeEntity {
    @Id
    private BigInteger id;

    private BigInteger adminRoleId;
    private BigInteger channelId;
}
