package isthereanydeal.app.games.service;

import isthereanydeal.app.games.domain.Game;
import isthereanydeal.app.games.dto.GameResponseDto;
import isthereanydeal.app.games.repository.GameRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${deals.api.key}")
    private String apiKey;

    public List<GameResponseDto> fetchAndSaveGames() {
        String url = "https://api.isthereanydeal.com/deals/v2";
        Map<String, String> params = Map.of(
                "key", apiKey,
                "nondeals", "true",
                "country", "KR",
                "filter", "N4IgLgngDgpiBcBtAjAXQDQgMYFcwNAFsBLAOwQCYBWTQgQwA8FkAGFgX3aA",
                "sort", "-trending"
        );

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url + "?key={key}&nondeals={nondeals}&country={country}&filter={filter}&sort={sort}", JsonNode.class, params);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("API 요청 실패");
        }

        List<Game> saved = new ArrayList<>();
        List<Game> resultList = new ArrayList<>();
        for (JsonNode gameNode : response.getBody().path("list")) {
            JsonNode deal = gameNode.get("deal");
            JsonNode assetsNode = gameNode.get("assets");
            String banner = null;
            if (assetsNode != null && assetsNode.isObject()) {
                Iterator<String> fieldNames = assetsNode.fieldNames();
                List<String> keys = new ArrayList<>();
                fieldNames.forEachRemaining(keys::add);
                Collections.reverse(keys); // reversed iteration

                for (String key : keys) {
                    if (key.startsWith("banner")) {
                        banner = assetsNode.get(key).asText();
                        break;
                    }
                }
            }

            String name = gameNode.get("title").asText();

            Game newGame = Game.builder()
                    .id(gameNode.get("id").asText())
                    .name(gameNode.get("title").asText())
                    .cut(deal.get("cut").asInt())
                    .currentPrice(deal.get("price").get("amountInt").asInt())
                    .regularPrice(deal.get("regular").get("amountInt").asInt())
                    .lowestPrice(deal.get("historyLow").get("amountInt").asInt())
                    .url(deal.get("url").asText())
                    .endTime(parseOptionalDate(deal.get("expiry")))
                    .imageUrl(banner)
                    .build();
            saved.add(newGame);

            Optional<Game> existing = gameRepository.findByName(name);
            if (existing.isPresent()) {
                existing.get().updateFrom(newGame);
            } else {
                resultList.add(newGame);
            }
        }
        gameRepository.saveAll(resultList);

        return saved.stream().map(GameResponseDto::from).toList();
    }

    private ZonedDateTime parseOptionalDate(JsonNode expiryNode) {
        if (expiryNode == null || expiryNode.isNull()) return null;
        return OffsetDateTime.parse(expiryNode.asText()).atZoneSameInstant(ZoneOffset.UTC);
    }
}
