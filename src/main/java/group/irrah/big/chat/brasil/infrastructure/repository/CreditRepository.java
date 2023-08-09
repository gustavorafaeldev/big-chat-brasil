package group.irrah.big.chat.brasil.infrastructure.repository;

import group.irrah.big.chat.brasil.domain.model.Credits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credits, Long> {

  Optional<Credits> findByClientId(Long id);
}
