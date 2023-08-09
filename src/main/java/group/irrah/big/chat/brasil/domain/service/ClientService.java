package group.irrah.big.chat.brasil.domain.service;

import group.irrah.big.chat.brasil.api.exception.IlegalException;
import group.irrah.big.chat.brasil.api.exception.NotFoundException;
import group.irrah.big.chat.brasil.api.request.ClientRequest;
import group.irrah.big.chat.brasil.api.request.SmsSendingRequest;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.domain.enums.Plan;
import group.irrah.big.chat.brasil.domain.mapper.ClientMapper;
import group.irrah.big.chat.brasil.domain.model.Client;
import group.irrah.big.chat.brasil.domain.model.Credits;
import group.irrah.big.chat.brasil.infrastructure.repository.ClientRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa o serviço do cliente.
 */
@Service
public class ClientService {

  @Autowired
  private ClientMapper mapper;
  @Autowired
  private ClientRespository repository;
  @Autowired
  private CreditService creditService;


  /**
   * Este método cria um cliente na base de dados se ele já não existir, a validação é feita pelo CPF
   * @param request
   * @return ClientResponse
   */
  public ClientResponse registerClient(ClientRequest request) {
    Client clientExists = repository.findByCpf(request.getCpf());
    if (clientExists != null) {
      throw new IlegalException(String.format("Cliente com CPF %s já cadastrado na base de dados.", request.getCpf()));
    }

    Client client = repository.save(mapper.toEntity(request));
    return mapper.toResponse(client);
  }

  /**
   * Busca o cliente pelo seu ID passado como parâmetro, estoura uma exceção personalizada caso não encontre o cliente
   * com esse id.
   * @param id
   * @return ClientResponse
   */
  public ClientResponse findClientById(Long id) {
    Client client = findClient(id);
    return mapper.toResponse(client);
  }

  /**
   * Retorna uma lista de clientes
   * @return List<ClienteResponse>
   */
  public List<ClientResponse> findAllClients() {
    return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
  }

  /**
   * Atualiza o cliente com a request recebida da requisição, apenas CNPJ e COMPANY NAME é opcional então, todos os demais
   * atributos são obrigátorios, evitando de algum atributo ficar em branco.
   * @param id
   * @param request
   * @return ClientResponse
   */
  public ClientResponse updateClient(Long id, ClientRequest request) {
    Client clientExists = findClient(id);
    clientExists.setName(request.getName());
    clientExists.setEmail(request.getEmail());
    clientExists.setPhone_number(request.getPhone_number());
    clientExists.setCpf(request.getCpf());

    if (!request.getCnpj().isEmpty()) {
      clientExists.setCnpj(request.getCnpj());
    }
    if (!request.getCompany_name().isEmpty()) {
      clientExists.setCompany_name(request.getCompany_name());
    }

    return mapper.toResponse(repository.save(clientExists));
  }

  /**
   * Delete cliente pelo id, caso não exista cliente com ID passado como parâmetro, estoura uma exceção personalizada
   * @param id
   */
  public void deleteClient(Long id) {
    Client client = findClient(id);
    repository.delete(client);
  }

  /**
   * Método para envio de SMS, primeiro verifica o tipo de plano, logo em seguida valida se tem créditos suficientes
   * para o envio da mensagem, e estoura uma exceção personalizada caso o cliente não tenha crédito para realizar o
   * envio da SMS. Caso a mensagem seja enviada é atualizado na tabela de créditos o saldo do cliente.
   * @param request
   */
  public void sendingSms(SmsSendingRequest request) {
    Credits credit = creditService.findCreditByClient(request.getClientId());

    Plan plan = Plan.valueOf(credit.getPlan());
    if (plan.equals(Plan.PRE_PAGO)) {
      if (credit.getCredits() < 0.25) {
        throw new IlegalException("Você não possui créditos suficientes para o envio do SMS");
      }
      credit.setCredits(credit.getCredits() - 0.25);
      creditService.repository.save(credit);
    } else if (plan.equals(Plan.POS_PAGO)) {
      if (credit.getCredit_usage() + 0.25 > credit.getCredit_limit()) {
        throw new IlegalException("Você não possui créditos suficientes para o envio do SMS");
      }
      Double creditUsage = credit.getCredit_usage();
      credit.setCredit_usage(creditUsage + 0.25);
      creditService.repository.save(credit);
    }
  }

  private Client findClient(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Nenhum cliente encontrado com ID: %s", id)));
  }
}
