package isthereanydeal.app.games.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

import isthereanydeal.app.games.domain.*;
import isthereanydeal.app.games.repository.*;
import isthereanydeal.app.games.service.*;

@Service
@RequiredArgsConstructor
public class PopularDealsService {
    private final PopularDealsRepository popularDealsRepository;

    public void savePopularDeals(List<PopularDeals> popularDeals) {
        if (popularDeals == null || popularDeals.isEmpty()) {
            return; // No deals to save
        }
        popularDealsRepository.deleteAll();  // Clear existing deals before saving new ones
        for (PopularDeals deal : popularDeals) {
            if (deal.getId() == null || deal.getGame() == null) {
                continue; // Skip invalid deals
            }
            popularDealsRepository.save(deal);
        }
    }
}
