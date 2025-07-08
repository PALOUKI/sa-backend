package tech.root.hsa.sa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.root.hsa.sa.entities.Client;
import tech.root.hsa.sa.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createClient(Client client) {
        Client existClient = clientRepository.findByEmail(client.getEmail());
        if (existClient == null) {
            this.clientRepository.save(client);
        }
    }

    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }


    public Client findById(int id) {
       Optional<Client> optionalClient = clientRepository.findById(id);
       return optionalClient.orElseThrow(
               () -> new EntityNotFoundException("Client with id " + id + " not found")
       );
    }

    public Client readOrCreateClient(Client client){
        Client existClient = clientRepository.findByEmail(client.getEmail());
        if (existClient == null) {
            existClient = this.clientRepository.save(client);
        }
        return existClient;
    }

    public void updateClient(int id, Client client) {
        Client clientInBDD = this.findById(id);
        if(clientInBDD.getId() == client.getId()){
            clientInBDD.setEmail(client.getEmail());
            clientInBDD.setTelephone(client.getTelephone());
            this.clientRepository.save(clientInBDD);
        }


    }
}
