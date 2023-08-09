package group.irrah.big.chat.brasil.domain.service;

import group.irrah.big.chat.brasil.api.exception.IlegalException;
import group.irrah.big.chat.brasil.api.exception.NotFoundException;
import group.irrah.big.chat.brasil.api.request.AddCreditsRequest;
import group.irrah.big.chat.brasil.api.request.ChangePlanRequest;
import group.irrah.big.chat.brasil.api.request.CreditRequest;
import group.irrah.big.chat.brasil.api.request.UpdateLimitRequest;
import group.irrah.big.chat.brasil.api.response.BalanceResponse;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.enums.Plan;
import group.irrah.big.chat.brasil.domain.mapper.CreditMapper;
import group.irrah.big.chat.brasil.domain.model.Client;
import group.irrah.big.chat.brasil.domain.model.Credits;
import group.irrah.big.chat.brasil.infrastructure.repository.ClientRespository;
import group.irrah.big.chat.brasil.infrastructure.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe que representa o serviço de créditos do cliente.
 */
@Service
public class CreditService {

  @Autowired
  public CreditRepository repository;
  @Autowired
  private ClientRespository clientRespository;
  @Autowired
  private CreditMapper mapper;

  /**
   * Método que cria um registro na tabela de credits, para associar crédito a um cliente, primeiro valida se o
   * cliente existe na base de dados, caso existir continua o fluxo , setando o cliente na classe de Credit,
   * os atributos de limite de crédito e credito são opcionais na requisição, então se não vierem setados, por padrão
   * é setá como 0.0 ao criar o registro.
   * Caso já exista um registro de crédito para esse cliente, estoura uma exceção.
   * @param request
   * @return CreditResponse
   */
  public CreditResponse registerCredit(CreditRequest request) {
    Client client = clientRespository.findById(request.getClient_id())
            .orElseThrow(() -> new NotFoundException(String.format("Nenhum cliente encontrado com ID: %s", request.getClient_id())));

    Optional<Credits> optionalCredits = repository.findByClientId(request.getClient_id());
    if (optionalCredits.isPresent()) {
      throw new IlegalException("Este cliente já possui um registro de crédito cadastrado, por favor atualize o existente");
    }

    Credits credit = mapper.toEntity(request);
    credit.setClient(client);
    credit.setPlan(request.getPlan().toString());

    if (request.getCredit_limit() != null) {
      credit.setCredit_limit(request.getCredit_limit());
    } else {
      credit.setCredit_limit(0.0);
    }

    if (request.getCredits() != null) {
      credit.setCredits(request.getCredits());
    } else {
      credit.setCredits(0.0);
    }
    credit.setCredit_usage(0.0);
    return mapper.toResponse(repository.save(credit));
  }

  /**
   * Retorna o registro de crédito para o cliente passado como parâmetro, caso não exista estoura uma exceção personalizada.
   * @param clientId
   * @return CreditResponse
   */
  public CreditResponse findCreditsByClient(Long clientId) {
    return mapper.toResponse(repository.findByClientId(clientId)
            .orElseThrow(() -> new NotFoundException(String.format("Nenhum cliente encontrado com ID: %s", clientId))));
  }

  /**
   * Método adiciona crédito para o cliente, que é o crédito que ele já tem mais o que veio como parâmetro.
   * @param clientId
   * @param request
   * @return CreditResponse
   */
  public CreditResponse addCredits(Long clientId, AddCreditsRequest request) {
    Credits credits = findCreditByClient(clientId);
    Double creditExists = credits.getCredits();
    credits.setCredits(creditExists + request.getCredit());
    return mapper.toResponse(repository.save(credits));
  }

  /**
   * Método para consultar saldo do cliente, caso não exista registro na tabela de crédito associado ao cliente recebido
   * como parâmetro, estoura uma exceção personalizada. Se existir verifica se é do tipo PRE_PAGO, retorna os créditos
   * ainda restantes. Caso POS_PAGO retorna o saldo, que seria o limite de crédito que o cliente tem subtraindo
   * com o que ele já usou.
   * @param clientId
   * @return BalanceResponse
   */
  public BalanceResponse checkBalance(Long clientId) {
    Credits credits = findCreditByClient(clientId);

    if (credits.getPlan().equals(Plan.PRE_PAGO.toString())) {
      return BalanceResponse.builder()
              .balance(credits.getCredits())
              .build();
    }

    return BalanceResponse.builder()
            .balance(credits.getCredit_limit() - credits.getCredit_usage())
            .build();
  }

  /**
   * Atualiza o limite de crédito que o cliente tem, substituindo o que estava no registro anterior.
   * @param clientId
   * @param request
   * @return CreditResponse
   */
  public CreditResponse updateLimit(Long clientId, UpdateLimitRequest request) {
    Credits credits = findCreditByClient(clientId);
    credits.setCredit_limit(request.getNewLimit());
    Credits save = repository.save(credits);
    return mapper.toResponse(save);
  }

  /**
   * Método que altera o plano do cliente, de PRE_PAGO para POS_PAGO e vice versa.
   * @param clientId
   * @param request
   * @return CreditResponse
   */
  public CreditResponse changePlan(Long clientId, ChangePlanRequest request) {
    Credits credits = findCreditByClient(clientId);
    credits.setPlan(request.getPlan().toString());
    Credits save = repository.save(credits);
    return mapper.toResponse(save);
  }

  /**
   * Rertorna registro de crédito associado a um cliente.
   * @param clientId
   * @return Credits
   */
  public Credits findCreditByClient(Long clientId) {
    return repository.findByClientId(clientId)
            .orElseThrow(() -> new NotFoundException(String
                    .format("Nenhum registro de crédito encontrado para cliente com ID: %s", clientId)));
  }
}
