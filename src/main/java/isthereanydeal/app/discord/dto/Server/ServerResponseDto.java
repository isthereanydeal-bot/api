package isthereanydeal.app.discord.dto.Server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import isthereanydeal.app.discord.domain.Server;

@Getter
@AllArgsConstructor
@Builder
public class ServerResponseDto {
    private BigInteger id;
    private BigInteger adminRoleId;
    private BigInteger channelId;

    public static  ServerResponseDto from(Server server) {
        return ServerResponseDto.builder()
                .id(server.getId())
                .adminRoleId(server.getAdminRoleId())
                .channelId(server.getChannelId())
                .build();
    }
}
