package isthereanydeal.app.games.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import isthereanydeal.app.games.domain.*;
import isthereanydeal.app.games.repository.*;

@Service
@RequiredArgsConstructor
public class GameDealService {
    private final GameDealRepository gameDealRepository;

    public GameDeal upsertGamePrice(GameDeal gameDeal) {
        return gameDealRepository.findByGameIdAndLocale(gameDeal.getGame().getId(), gameDeal.getLocale())
                .map(existingPrice -> {
                    existingPrice.setCurrentPrice(gameDeal.getCurrentPrice());
                    existingPrice.setRegularPrice(gameDeal.getRegularPrice());
                    existingPrice.setLowestPrice(gameDeal.getLowestPrice());
                    existingPrice.setCut(gameDeal.getCut());
                    existingPrice.setEndTime(gameDeal.getEndTime() != null ? gameDeal.getEndTime() : existingPrice.getEndTime());
                    existingPrice.setStoreName(gameDeal.getStoreName());
                    return gameDealRepository.save(existingPrice);
                })
                .orElseGet(() -> gameDealRepository.save(gameDeal));
    }
}
