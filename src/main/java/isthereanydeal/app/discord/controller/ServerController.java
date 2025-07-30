package isthereanydeal.app.discord.controller;

import isthereanydeal.app.discord.dto.Server.*;
import isthereanydeal.app.discord.repository.*;
import isthereanydeal.app.discord.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private final ServerRepository serverRepository;

    @GetMapping()
    public ResponseEntity<ServerResponseDto> getServer(@RequestParam BigInteger serverId) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server not found"));
        return ResponseEntity.ok(ServerResponseDto.from(server));
    }

    @PostMapping()
    public ResponseEntity<ServerResponseDto> createServer(@RequestBody ServerCreateRequestDto serverRequestDto) {
        Server server = new Server();
        server.setId(serverRequestDto.getId());
        Server savedServer = serverRepository.save(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(ServerResponseDto.from(savedServer));
    }
}
