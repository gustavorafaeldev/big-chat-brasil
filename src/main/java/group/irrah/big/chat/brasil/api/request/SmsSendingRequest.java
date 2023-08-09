package group.irrah.big.chat.brasil.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Classe de request utilizada quando o método enviar mensagem é utilizado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendingRequest {

  @NotNull(message = "Cliente ID não pode ser nulo")
  private Long clientId;
  @NotBlank(message = "Telefone não pode ser em branco")
  @NotNull(message = "Telefone não pode ser nulo")
  @Pattern(regexp = "(^[0-9]{10,11}$)", message = "Telefone precisa ser um número de telefone válido")
  private String phoneNumber;
  private Boolean whatsApp;
  private Boolean text;
}
