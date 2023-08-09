package group.irrah.big.chat.brasil.api.controller;

import group.irrah.big.chat.brasil.api.request.AddCreditsRequest;
import group.irrah.big.chat.brasil.api.request.ChangePlanRequest;
import group.irrah.big.chat.brasil.api.request.CreditRequest;
import group.irrah.big.chat.brasil.api.request.UpdateLimitRequest;
import group.irrah.big.chat.brasil.api.response.BalanceResponse;
import group.irrah.big.chat.brasil.api.response.CreditResponse;
import group.irrah.big.chat.brasil.domain.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("credit")
@RestController
@RequiredArgsConstructor
public class CreditsController {

  private final CreditService service;

  @PostMapping
  ResponseEntity<CreditResponse> registerCredit(@Valid @RequestBody CreditRequest request) {
    return ResponseEntity.status(CREATED).body(service.registerCredit(request));
  }

  @GetMapping("{client_id}")
  ResponseEntity<CreditResponse> findCreditByClient(@PathVariable Long client_id) {
    return ResponseEntity.ok(service.findCreditsByClient(client_id));
  }

  @GetMapping("balance/{client_id}")
  ResponseEntity<BalanceResponse> checkBalance(@PathVariable Long client_id) {
    return ResponseEntity.ok(service.checkBalance(client_id));
  }

  @PatchMapping("add-credits/{client_id}")
  ResponseEntity<CreditResponse> addCredits(@PathVariable Long client_id, @Valid @RequestBody AddCreditsRequest request) {
    return ResponseEntity.status(204).body(service.addCredits(client_id, request));
  }

  @PatchMapping("update-limit/{client_id}")
  ResponseEntity<CreditResponse> updateLimit(@PathVariable Long client_id, @Valid @RequestBody UpdateLimitRequest request) {
    return ResponseEntity.status(204).body(service.updateLimit(client_id, request));
  }

  @PatchMapping("change-plan/{client_id}")
  ResponseEntity<CreditResponse> changePlan(@PathVariable Long client_id, @Valid @RequestBody ChangePlanRequest request) {
    return ResponseEntity.status(204).body(service.changePlan(client_id, request));
  }
}
