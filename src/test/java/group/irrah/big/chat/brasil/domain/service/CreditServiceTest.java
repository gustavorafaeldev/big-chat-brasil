package group.irrah.big.chat.brasil.domain.service;

import group.irrah.big.chat.brasil.api.request.*;
import group.irrah.big.chat.brasil.api.response.BalanceResponse;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.enums.Plan;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CreditServiceTest {

  public static final Long ID = 5L;
  public static final String NAME = "Gustavo";
  public static final String EMAIL = "grlrafael@outlook.com";
  public static final String PHONE = "44984468133";
  public static final String CPF = "11111111111";
  public static final String CNPJ = "11111111111111";
  public static final String COMPANY = "Empresa de Envio de Mensagens";

  @InjectMocks
  private CreditService creditService;

  @Mock
  private CreditRepository creditRepository;

  @Mock
  private ClientRespository clientRespository;

  @Mock
  private CreditMapper creditMapper;

  private Optional<Credits> creditsOptional;
  private Credits credits;
  private CreditResponse creditResponse;
  private CreditRequest creditRequest;
  private Client client;
  private Optional<Client> clientOptional;
  private ClientResponse clientResponse;
  private ClientRequest clientRequest;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startClient();

    when(creditMapper.toEntity(any())).thenReturn(credits);
    when(creditMapper.toResponse(any())).thenReturn(creditResponse);
  }

  @Test
  void whenCreateWhenReturnSuccess() {
    when(clientRespository.findById(anyLong())).thenReturn(clientOptional);
    when(creditRepository.save(any())).thenReturn(credits);

    CreditResponse response = creditService.registerCredit(creditRequest);

    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
    assertEquals(1L, response.getId());
  }

  @Test
  void whenFindByClientThenReturnAnCreditInstance() {
    when(creditRepository.findById(anyLong())).thenReturn(creditsOptional);
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);

    CreditResponse response = creditService.findCreditsByClient(ID);

    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
    assertEquals(1L, response.getId());
  }

  @Test
  void whenAddCredtisThenReturnCreditInstance() {
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);
    when(creditRepository.save(any())).thenReturn(credits);

    CreditResponse response = creditService.addCredits(1L, new AddCreditsRequest(0.25));
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
    assertEquals(0.25, response.getCredits());
  }

  @Test
  void whenCheckBalanceThenReturnBalanceInstance() {
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);

    BalanceResponse balanceResponse = creditService.checkBalance(1L);

    assertNotNull(balanceResponse);
    assertEquals(BalanceResponse.class, balanceResponse.getClass());
    assertEquals(0.25, balanceResponse.getBalance());
  }

  @Test
  void whenUpdateLimitWithSuccess() {
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);
    when(creditRepository.save(any())).thenReturn(credits);

    CreditResponse response = creditService.updateLimit(1L, new UpdateLimitRequest(1.00));
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
    assertEquals(0.25, response.getCredit_limit());
  }

  @Test
  void whenChangePlanThenReturnCreditInstance() {
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);
    when(creditRepository.save(any())).thenReturn(credits);

    CreditResponse response = creditService.changePlan(1L, new ChangePlanRequest(Plan.PRE_PAGO));
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
    assertEquals(Plan.PRE_PAGO, response.getPlan());
  }

  @Test
  void findCreditByClient() {
    when(creditRepository.findByClientId(anyLong())).thenReturn(creditsOptional);

    CreditResponse response = creditService.findCreditsByClient(1L);
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getClass());
  }

  private void startClient() {
    creditsOptional = Optional.of(new Credits(1L, 0.25, 0.25, "PRE_PAGO", 0.0, null));
    credits = new Credits(1L, 0.25, 0.25, "PRE_PAGO", 0.0, null);
    creditResponse = new CreditResponse(1L, 0.25, 0.25, 0.0, Plan.PRE_PAGO);
    creditRequest = new CreditRequest(0.25, 0.25, Plan.PRE_PAGO, 1L);


    clientRequest = new ClientRequest(NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY);
    clientResponse = new ClientResponse(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null);
    clientOptional = Optional.of(new Client(ID, NAME, EMAIL, PHONE, CPF, CNPJ, COMPANY, null));
  }
}