package group.irrah.big.chat.brasil.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Classe que representa o crédito do cliente.
 */
@Entity(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credits {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private Double credits;
  private Double credit_limit;
  private String plan;
  private Double credit_usage;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  @JsonIgnore
  private Client client;
}
