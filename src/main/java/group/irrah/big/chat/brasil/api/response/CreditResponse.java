package group.irrah.big.chat.brasil.api.response;

import group.irrah.big.chat.brasil.domain.enums.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe que representa o response do credito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditResponse {

  private Long id;
  private Double credits;
  private Double credit_limit;
  private Double credit_usage;
  private Plan plan;
}
