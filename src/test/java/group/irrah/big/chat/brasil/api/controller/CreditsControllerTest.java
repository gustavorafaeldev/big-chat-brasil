package group.irrah.big.chat.brasil.api.controller;

import group.irrah.big.chat.brasil.api.request.AddCreditsRequest;
import group.irrah.big.chat.brasil.api.request.ChangePlanRequest;
import group.irrah.big.chat.brasil.api.request.CreditRequest;
import group.irrah.big.chat.brasil.api.request.UpdateLimitRequest;
import group.irrah.big.chat.brasil.api.response.BalanceResponse;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.enums.Plan;
import group.irrah.big.chat.brasil.domain.model.Credits;
import group.irrah.big.chat.brasil.domain.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CreditsControllerTest {

  private Credits credits;
  private CreditRequest creditRequest;
  private CreditResponse creditResponse;
  private Optional<Credits> creditsOptional;
  @InjectMocks
  private CreditsController controller;

  @Mock
  private CreditService service;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startUser();
  }

  @Test
  void registerCredit() {
    when(service.registerCredit(any())).thenReturn(creditResponse);

    ResponseEntity<CreditResponse> response = controller.registerCredit(creditRequest);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(CreditResponse.class, response.getBody().getClass());
    assertEquals(1L, response.getBody().getId());
  }

  @Test
  void findCreditByClient() {
    when(service.findCreditsByClient(anyLong())).thenReturn(creditResponse);

    ResponseEntity<CreditResponse> response = controller.findCreditByClient(1L);
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(CreditResponse.class, response.getBody().getClass());
    assertEquals(1L, response.getBody().getId());
  }

  @Test
  void checkBalance() {
    when(service.checkBalance(anyLong())).thenReturn(new BalanceResponse(0.25));

    ResponseEntity<BalanceResponse> response = controller.checkBalance(1L);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response);
    assertEquals(BalanceResponse.class, response.getBody().getClass());
    assertEquals(0.25, response.getBody().getBalance());
  }

  @Test
  void addCredits() {
    when(service.addCredits(anyLong(), any())).thenReturn(creditResponse);

    ResponseEntity<CreditResponse> response = controller.addCredits(1L, new AddCreditsRequest(0.25));

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getBody().getClass());
    assertEquals(0.25, response.getBody().getCredits());
  }

  @Test
  void updateLimit() {
    when(service.updateLimit(anyLong(), any())).thenReturn(creditResponse);

    ResponseEntity<CreditResponse> response = controller.updateLimit(1L, new UpdateLimitRequest(0.25));

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getBody().getClass());
    assertEquals(0.25, response.getBody().getCredits());
  }

  @Test
  void changePlan() {
    when(service.changePlan(anyLong(), any())).thenReturn(creditResponse);

    ResponseEntity<CreditResponse> response = controller.changePlan(1L, new ChangePlanRequest(Plan.PRE_PAGO));

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNotNull(response);
    assertEquals(CreditResponse.class, response.getBody().getClass());
    assertEquals(Plan.PRE_PAGO, response.getBody().getPlan());
  }

  private void startUser() {
    creditsOptional = Optional.of(new Credits(1L, 0.25, 0.25, "PRE_PAGO", 0.0, null));
    credits = new Credits(1L, 0.25, 0.25, "PRE_PAGO", 0.0, null);
    creditResponse = new CreditResponse(1L, 0.25, 0.25, 0.0, Plan.PRE_PAGO);
    creditRequest = new CreditRequest(0.25, 0.25, Plan.PRE_PAGO, 1L);
  }
}