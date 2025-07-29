package isthereanydeal.app.discord.dto.Server;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class ServeCreateRequestDto {
    @NotNull
    private BigInteger id;
}
