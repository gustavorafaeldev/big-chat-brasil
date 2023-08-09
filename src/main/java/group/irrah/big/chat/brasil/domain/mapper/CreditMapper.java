package group.irrah.big.chat.brasil.domain.mapper;

import group.irrah.big.chat.brasil.api.request.CreditRequest;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.model.Credits;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Classe que converte entidade para response e vice versa para Credit
 */
@Component
@RequiredArgsConstructor
public class CreditMapper {

  private final ModelMapper mapper;

  public Credits toEntity(CreditRequest request) {
    return mapper.map(request, Credits.class);
  }

  public CreditResponse toResponse(Credits entity) {
    return mapper.map(entity, CreditResponse.class);
  }
}
