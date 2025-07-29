package isthereanydeal.app.games.controller;

import isthereanydeal.app.games.dto.GameResponseDto;
import isthereanydeal.app.games.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameResponseDto>> getDeals() {
        List<GameResponseDto> result = gameService.fetchAndSaveGames();
        return ResponseEntity.ok(result);
    }
}
