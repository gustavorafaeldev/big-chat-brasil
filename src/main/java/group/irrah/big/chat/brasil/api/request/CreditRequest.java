package group.irrah.big.chat.brasil.api.request;

import group.irrah.big.chat.brasil.domain.enums.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * Classe de request utilizada quando o método de registrar crédito é chamado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequest {

  private Double credits;
  private Double credit_limit;
  @NotNull(message = "Plano não pode ser nulo")
  private Plan plan;
  @NotNull(message = "Cliente ID não pode ser nulo")
  private Long client_id;
}
