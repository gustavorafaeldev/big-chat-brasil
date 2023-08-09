package group.irrah.big.chat.brasil.api.controller;

import group.irrah.big.chat.brasil.api.request.ClientRequest;
import group.irrah.big.chat.brasil.api.request.SmsSendingRequest;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.domain.model.Client;
import group.irrah.big.chat.brasil.domain.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientControllerTest {

  public static final Long ID = 1L;
  public static final String NAME = "Gustavo";
  public static final String EMAIL = "grlrafael@outlook.com";
  public static final String PHONE = "44984468133";
  public static final String CPF = "11111111111";
  public static final String CNPJ = "11111111111111";
  public static final String COMPANY = "Empresa de Envio de Mensagens";

  private Client client;
  private Optional<Client> clientOptional;
  private ClientResponse clientResponse;
  private ClientRequest clientRequest;
  private SmsSendingRequest smsSendingRequest;

  @InjectMocks
  private ClientController controller;

  @Mock
  private ClientService service;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startUser();
  }

  @Test
  void registerClient() {
    when(service.registerClient(any())).thenReturn(clientResponse);

    ResponseEntity<ClientResponse> response = controller.registerClient(clientRequest);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(ClientResponse.class, response.getBody().getClass());
    assertEquals(ID, response.getBody().getId());

  }

  @Test
  void sendingSms() {
    doNothing().when(service).sendingSms(smsSendingRequest);
    ResponseEntity<Void> response = controller.sendingSms(smsSendingRequest);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    verify(service, times(1)).sendingSms(any());
  }

  @Test
  void findClientById() {
    when(service.findClientById(anyLong())).thenReturn(clientResponse);

    ResponseEntity<ClientResponse> response = controller.findClientById(ID);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ClientResponse.class, response.getBody().getClass());
    assertEquals(ID, response.getBody().getId());
  }

  @Test
  void findAllClients() {
    when(service.findAllClients()).thenReturn(Collections.singletonList(clientResponse));

    ResponseEntity<List<ClientResponse>> response = controller.findAllClients();

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ClientResponse.class, response.getBody().get(0).getClass());
    assertEquals(ID, response.getBody().get(0).getId());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void updateClient() {
    when(service.updateClient(anyLong(), any())).thenReturn(clientResponse);

    ResponseEntity<ClientResponse> response = controller.updateClient(ID, clientRequest);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertEquals(ClientResponse.class, response.getBody().getClass());
    assertEquals(ID, response.getBody().getId());
  }

  @Test
  void deleteClient() {
    doNothing().when(service).deleteClient(any());

    ResponseEntity<Void> response = controller.deleteClient(ID);
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(service, times(1)).deleteClient(any());
  }

  private void startUser() {
    client = new Client(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null);
    clientRequest = new ClientRequest(NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY);
    clientResponse = new ClientResponse(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null);
    clientOptional = Optional.of(new Client(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null));

    smsSendingRequest = new SmsSendingRequest(1L, "44984468133", false, true);

  }
}