package tech.root.hsa.sa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.root.hsa.sa.entities.Client;
import tech.root.hsa.sa.service.ClientService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void create(@RequestBody Client client) {
        this.clientService.createClient(client);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Client> getAllClients() {
        return this.clientService.getAllClients();
    }

    @GetMapping(path="{id}", produces = APPLICATION_JSON_VALUE)
    public Client getClientById(@PathVariable int id) {
        return this.clientService.findById(id);
    }

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public void updateClient(@PathVariable int id, @RequestBody Client client) {
        clientService.updateClient(id, client);
    }
}
