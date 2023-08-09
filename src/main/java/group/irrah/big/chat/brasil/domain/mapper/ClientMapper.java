package group.irrah.big.chat.brasil.domain.mapper;

import group.irrah.big.chat.brasil.api.request.ClientRequest;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Classe que converte entidade para response e vice versa para Client
 */
@Component
@RequiredArgsConstructor
public class ClientMapper {

  private final ModelMapper mapper;

  public Client toEntity(ClientRequest request) {
    return mapper.map(request, Client.class);
  }

  public ClientResponse toResponse(Client entity) {
    return mapper.map(entity, ClientResponse.class);
  }
}
