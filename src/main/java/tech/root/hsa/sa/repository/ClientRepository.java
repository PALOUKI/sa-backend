package tech.root.hsa.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.root.hsa.sa.entities.Client;
import tech.root.hsa.sa.service.ClientService;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    public Client findByEmail(String email);

}
