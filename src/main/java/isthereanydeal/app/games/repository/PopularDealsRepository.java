package isthereanydeal.app.games.repository;

import isthereanydeal.app.games.domain.PopularDeals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository

public interface PopularDealsRepository extends JpaRepository<PopularDeals, String> {
}
