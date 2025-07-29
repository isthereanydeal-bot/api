package isthereanydeal.app.games.controller;

import isthereanydeal.app.games.dto.GameSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import isthereanydeal.app.games.application.*;
import isthereanydeal.app.games.dto.GameResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final PopularDealsApplication popularDealsApplication;
    private final GameApplication gameApplication;

    @GetMapping("/deals/{countryCode}")
    @ResponseBody
    public ResponseEntity<List<GameResponseDto>> getDeals(@PathVariable String countryCode) {
        if (countryCode == null || countryCode.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<GameResponseDto> result = popularDealsApplication.fetchAndSaveGames(countryCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<GameSearchResponseDto>> searchGames(@RequestParam String query) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<GameSearchResponseDto> games = gameApplication.searchGames(query);
        return ResponseEntity.ok(games);
    }

}
