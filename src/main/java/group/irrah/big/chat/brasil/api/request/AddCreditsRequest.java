package group.irrah.big.chat.brasil.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Classe de request utilizada quando o método de adicionar crédito a um cliente é chamado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCreditsRequest {

  @NotNull(message = "Créditos não pode ser nulo")
  private Double credit;
}
