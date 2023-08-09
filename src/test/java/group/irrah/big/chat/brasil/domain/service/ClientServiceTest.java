package group.irrah.big.chat.brasil.domain.service;

import group.irrah.big.chat.brasil.api.request.ClientRequest;
import group.irrah.big.chat.brasil.api.request.SmsSendingRequest;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.enums.Plan;
import group.irrah.big.chat.brasil.domain.mapper.ClientMapper;
import group.irrah.big.chat.brasil.domain.mapper.CreditMapper;
import group.irrah.big.chat.brasil.domain.model.Client;
import group.irrah.big.chat.brasil.domain.model.Credits;
import group.irrah.big.chat.brasil.infrastructure.repository.ClientRespository;
import group.irrah.big.chat.brasil.infrastructure.repository.CreditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClientServiceTest {

  public static final Long ID = 5L;
  public static final String NAME = "Gustavo";
  public static final String EMAIL = "grlrafael@outlook.com";
  public static final String PHONE = "44984468133";
  public static final String CPF = "11111111111";
  public static final String CNPJ = "11111111111111";
  public static final String COMPANY = "Empresa de Envio de Mensagens";

  @InjectMocks
  private ClientService service;

  @Mock
  private CreditService creditService;

  @Mock
  private ClientRespository repository;

  @Mock
  private CreditRepository creditRepository;

  @Mock
  private ClientMapper clientMapper;

  @Mock
  private CreditMapper creditMapper;

  private Client client;
  private Optional<Client> clientOptional;
  private ClientResponse clientResponse;
  private ClientRequest clientRequest;
  private Optional<Credits> creditsOptional;
  private Credits credits;
  private CreditResponse creditResponse;
  private SmsSendingRequest smsSendingRequest;



  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startClient();

    when(clientMapper.toEntity(any())).thenReturn(client);
    when(clientMapper.toResponse(any())).thenReturn(clientResponse);

    when(creditMapper.toEntity(any())).thenReturn(credits);
    when(creditMapper.toResponse(any())).thenReturn(creditResponse);
  }


  @Test
  void whenCreateWhenReturnSuccess() {
    when(repository.save(any())).thenReturn(client);

    ClientResponse response = service.registerClient(clientRequest);

    assertNotNull(response);
    assertEquals(ClientResponse.class, response.getClass());
    assertEquals(ID, response.getId());
  }

  @Test
  void whenFindByIdThenReturnAnClientInstance() {
    when(repository.findById(anyLong())).thenReturn(clientOptional);

    ClientResponse response = service.findClientById(ID);

    assertNotNull(response);
    assertEquals(ClientResponse.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(EMAIL, response.getEmail());
  }

  @Test
  void whenFindAllThenReturnAnListOfClients() {
    when(repository.findAll()).thenReturn(Collections.singletonList(client));

    List<ClientResponse> responseList = service.findAllClients();

    assertNotNull(responseList);
    assertEquals(1, responseList.size());
    assertEquals(ClientResponse.class, responseList.get(0).getClass());

    assertEquals(ID, responseList.get(0).getId());
    assertEquals(NAME, responseList.get(0).getName());
    assertEquals(EMAIL, responseList.get(0).getEmail());
  }

  @Test
  void whenUpdateThenReturnSuccess() {
    when(repository.save(any())).thenReturn(client);
    when(repository.findById(any())).thenReturn(clientOptional);

    ClientResponse response = service.updateClient(ID, clientRequest);
    assertNotNull(response);
    assertEquals(ClientResponse.class, response.getClass());
    assertEquals(ID,  response.getId());
    assertEquals(NAME,  response.getName());
    assertEquals(EMAIL,  response.getEmail());
  }

  @Test
  void deleteWithSuccess() {
    when(repository.findById(anyLong())).thenReturn(clientOptional);
    doNothing().when(repository).delete(client);
    service.deleteClient(ID);
    verify(repository, times(1)).delete(any());
  }


  @Test
  void whenSendSmsWithSuccess() {
  }

  private void startClient() {
    client = new Client(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null);
    clientRequest = new ClientRequest(NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY);
    clientResponse = new ClientResponse(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null);
    clientOptional = Optional.of(new Client(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null));

    creditsOptional = Optional.of(new Credits(ID, 0.25, 0.25, "PRE_PAGO", 0.0, null));
    credits = new Credits(ID, 0.25, 0.25, "PRE_PAGO", 0.0, null);
    smsSendingRequest = new SmsSendingRequest(1L, "44984468133", false, true);
    creditResponse = new CreditResponse(ID, 0.25, 0.25, 0.0, Plan.PRE_PAGO);
  }
}