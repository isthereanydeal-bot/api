package isthereanydeal.app.games.application;

import com.fasterxml.jackson.databind.JsonNode;
import isthereanydeal.app.common.annotation.Application;
import isthereanydeal.app.games.application.common.GameAssetsUtils;
import lombok.RequiredArgsConstructor;

import isthereanydeal.app.games.service.*;
import isthereanydeal.app.games.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Application
@RequiredArgsConstructor
public class GameApplication {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${isthereanydeal.api.key}")
    private String apiKey;
    @Value("${isthereanydeal.game.search.url}")
    private String gameSearchUrl;


    public List<GameSearchResponseDto> searchGames(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList(); // Return empty list if query is null or empty
        }
        Map<String, String> params = Map.of(
                "key", apiKey,
                "title", query
        );
        String url = gameSearchUrl + "?key={key}&title={title}";
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class, params);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseStatusException(response.getStatusCode(), "Wrong response from API: " + response.getBody());
        }

        List<GameSearchResponseDto> results = new ArrayList<>();
        JsonNode data = response.getBody();
        if (Objects.requireNonNull(data).isEmpty()) {
            return results; // Return empty list if no data found
        }
        for (JsonNode gameNode : data) {
            JsonNode assetsNode = data.get("assets");
            String banner = GameAssetsUtils.GetBanner(assetsNode);

            GameSearchResponseDto game = GameSearchResponseDto.from(
                    gameNode.get("id").asText(),
                    gameNode.get("title").asText(),
                    gameNode.get("slug").asText(),
                    gameNode.get("mature").asBoolean(),
                    gameNode.get("type").asText(),
                    banner
            );

            results.add(game);
        }
        return results;
    }
}
