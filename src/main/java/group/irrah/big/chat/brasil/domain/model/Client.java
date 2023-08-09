package group.irrah.big.chat.brasil.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Classe que representa o cliente.
 */

@Entity(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String phone_number;
  private String cpf;
  private String cnpj;
  private String company_name;
  @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Credits credits;
}
