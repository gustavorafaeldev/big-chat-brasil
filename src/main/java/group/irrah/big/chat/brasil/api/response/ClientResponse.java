package group.irrah.big.chat.brasil.api.response;

import group.irrah.big.chat.brasil.domain.model.Credits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe que representa response do cliente
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

  private Long id;
  private String name;
  private String email;
  private String phone_number;
  private String cpf;
  private String cnpj;
  private String company_name;
  private Credits credits;
}
