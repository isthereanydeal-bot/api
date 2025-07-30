package isthereanydeal.app.discord.dto.Server;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class ServerCreateRequestDto {
    @NotNull
    private BigInteger id;
}
