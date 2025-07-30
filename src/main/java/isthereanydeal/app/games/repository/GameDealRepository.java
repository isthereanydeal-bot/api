package isthereanydeal.app.games.repository;

import isthereanydeal.app.games.domain.GameDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface GameDealRepository extends JpaRepository<GameDeal, Long> {
    Optional<GameDeal> findByGameIdAndLocale(String gameId, String locale);
}
