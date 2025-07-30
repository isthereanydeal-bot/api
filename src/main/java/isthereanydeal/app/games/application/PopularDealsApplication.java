package isthereanydeal.app.games.application;

import com.fasterxml.jackson.databind.JsonNode;
import isthereanydeal.app.common.annotation.Application;
import isthereanydeal.app.games.application.common.GameAssetsUtils;
import isthereanydeal.app.games.dto.GameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import isthereanydeal.app.games.service.*;
import isthereanydeal.app.games.domain.*;

@Application
@RequiredArgsConstructor
public class PopularDealsApplication {
    private final GameService gameService;
    private final PopularDealsService popularDealsService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final GameDealService gameDealService;
    @Value("${isthereanydeal.api.key}")
    private String apiKey;
    @Value("${isthereanydeal.deal.url}")
    private String dealUrl;

    public List<GameResponseDto> fetchAndSaveGames(String countryCode) {
        Map<String, String> params = Map.of(
                "key", apiKey,
                "nondeals", "true",
                "country", countryCode,
                "filter", "N4IgLgngDgpiBcBtAjAXQDQgMYFcwNAFsBLAOwQCYBWTQgQwA8FkAGFgX3aA",
                "sort", "-trending"
        );

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(dealUrl + "?key={key}&nondeals={nondeals}&country={country}&filter={filter}&sort={sort}", JsonNode.class, params);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseStatusException(response.getStatusCode(), "Wrong response from API: " + response.getBody());
        }

        List<GameResponseDto> saved = new ArrayList<>();
        List<PopularDeals> newDeals = new ArrayList<>();
        for (JsonNode gameNode : response.getBody().path("list")) {
            JsonNode deal = gameNode.get("deal");
            JsonNode assetsNode = gameNode.get("assets");
            String banner = GameAssetsUtils.GetBanner(assetsNode);

            String name = gameNode.get("title").asText();
            String id = gameNode.get("id").asText();

            Game newGame = Game.builder()
                    .id(id)
                    .name(name)
                    .url(deal.get("url").asText())
                    .banner(banner)
                    .build();

            GameDeal gameDeal = GameDeal.builder()
                    .game(newGame)
                    .locale(countryCode)
                    .currentPrice(deal.get("price").get("amountInt").asInt())
                    .regularPrice(deal.get("regular").get("amountInt").asInt())
                    .lowestPrice(deal.get("historyLow").get("amountInt").asInt())
                    .currency(deal.get("price").get("currency").asText())
                    .storeName(deal.get("shop").get("name").asText())
                    .cut(deal.get("cut").asInt())
                    .endTime(parseOptionalDate(deal.get("expiry")))
                    .build();

            PopularDeals popularDeal = PopularDeals.builder()
                    .game(newGame)
                    .build();
            newDeals.add(popularDeal);

            gameService.upsertGame(newGame);
            gameDealService.upsertGamePrice(gameDeal);
            saved.add(GameResponseDto.from(newGame, gameDeal));
        }
        popularDealsService.savePopularDeals(newDeals);
        return saved;
    }

    private ZonedDateTime parseOptionalDate(JsonNode expiryNode) {
        if (expiryNode == null || expiryNode.isNull()) return null;
        return OffsetDateTime.parse(expiryNode.asText()).atZoneSameInstant(ZoneOffset.UTC); // UTC로 저장해야 타임스탬프 만들기 편해요
    }

}
