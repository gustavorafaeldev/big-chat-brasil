package group.irrah.big.chat.brasil.api.controller;

import group.irrah.big.chat.brasil.api.request.ClientRequest;
import group.irrah.big.chat.brasil.api.request.SmsSendingRequest;
import group.irrah.big.chat.brasil.api.response.ClientResponse;
import group.irrah.big.chat.brasil.domain.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RequestMapping("client")
@RestController
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;

  @PostMapping
  ResponseEntity<ClientResponse> registerClient(@Valid @RequestBody ClientRequest request) {
    return ResponseEntity.status(CREATED).body(service.registerClient(request));
  }

  @PostMapping("send-sms")
  ResponseEntity<Void> sendingSms(@Valid @RequestBody SmsSendingRequest request) {
    service.sendingSms(request);
    return ResponseEntity.status(CREATED).build();
  }

  @GetMapping("{id}")
  ResponseEntity<ClientResponse> findClientById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findClientById(id));
  }

  @GetMapping
  ResponseEntity<List<ClientResponse>> findAllClients() {
    return ResponseEntity.ok().body(service.findAllClients());
  }

  @PutMapping("{id}")
  ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @Valid  @RequestBody ClientRequest request) {
    return ResponseEntity.status(204).body(service.updateClient(id, request));
  }

  @DeleteMapping("{id}")
  ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    service.deleteClient(id);
    return ResponseEntity.status(204).build();
  }
}
