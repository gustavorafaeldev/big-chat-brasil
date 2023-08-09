package group.irrah.big.chat.brasil.infrastructure.repository;

import group.irrah.big.chat.brasil.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRespository extends JpaRepository<Client, Long> {

  Client findByCpf(String cpf);
}

