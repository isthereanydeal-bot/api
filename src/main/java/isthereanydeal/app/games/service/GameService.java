package isthereanydeal.app.games.service;

import isthereanydeal.app.games.domain.Game;
import isthereanydeal.app.games.repository.GameRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game upsertGame(Game newGame) {
        return gameRepository.findById(newGame.getId())
                .map(existingGame -> {
                    existingGame.setName(newGame.getName());
                    existingGame.setUrl(newGame.getUrl());
                    existingGame.setBanner(newGame.getBanner());
                    return gameRepository.save(existingGame);
                })
                .orElseGet(() -> gameRepository.save(newGame));
    }
}