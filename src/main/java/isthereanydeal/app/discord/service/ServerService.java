package isthereanydeal.app.discord.service;


import isthereanydeal.app.discord.repository.ServerRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;

}
