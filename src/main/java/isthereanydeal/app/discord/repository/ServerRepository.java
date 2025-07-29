package isthereanydeal.app.discord.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;
import java.util.Optional;

import isthereanydeal.app.discord.domain.*;

@Repository
public interface ServerRepository extends JpaRepository<Server, BigInteger> {
    // Additional custom query methods can be defined here if needed
    // For example, to find a server by its name:
    // Optional<Server> findByName(String name);
}