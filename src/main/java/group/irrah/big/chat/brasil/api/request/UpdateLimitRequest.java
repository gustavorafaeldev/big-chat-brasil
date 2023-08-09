package group.irrah.big.chat.brasil.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Classe de request utilizada quando o método de atualizar o limite de crédito de um cliente é chamado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLimitRequest {

  @NotNull(message = "Novo limite não pode ser nulo")
  private Double newLimit;
}
