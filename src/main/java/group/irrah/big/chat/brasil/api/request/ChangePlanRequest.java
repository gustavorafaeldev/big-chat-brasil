package group.irrah.big.chat.brasil.api.request;

import group.irrah.big.chat.brasil.domain.enums.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Classe de request utilizada para quando o método de alterar o plano do cliente é chamado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePlanRequest {

  @NotNull(message = "Plano não pode ser nulo")
  private Plan plan;
}
