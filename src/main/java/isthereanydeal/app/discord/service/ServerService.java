package isthereanydeal.app.discord.service;


import isthereanydeal.app.discord.repository.ServerRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;

}
