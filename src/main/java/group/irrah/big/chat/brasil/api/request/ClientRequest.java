package group.irrah.big.chat.brasil.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Classe de request utilizada quando o método de registrar um cliente é chamado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

  @NotBlank(message = "Nome não pode ser em branco")
  @NotNull(message = "Nome não pode ser nulo")
  private String name;
  @NotBlank(message = "Email não pode ser em branco")
  @NotNull(message = "Email não pode ser nulo")
  @Email(message = "Digite um e-mail válido")
  private String email;
  @NotBlank(message = "Telefone não pode ser em branco")
  @NotNull(message = "Telefone não pode ser nulo")
  @Pattern(regexp = "(^[0-9]{10,11}$)", message = "Telefone precisa ser um número de telefone válido")
  private String phone_number;
  @NotBlank(message = "CPF não pode ser em branco")
  @NotNull(message = "CPF não pode ser nulo")
  private String cpf;
  private String cnpj;
  private String company_name;
}
