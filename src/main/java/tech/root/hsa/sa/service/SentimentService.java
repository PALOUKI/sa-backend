package tech.root.hsa.sa.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;
import tech.root.hsa.sa.entities.Client;
import tech.root.hsa.sa.entities.Sentiment;
import tech.root.hsa.sa.enums.TypeSentiment;
import tech.root.hsa.sa.repository.SentimentRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
public class SentimentService {
    private SentimentRepository sentimentRepository;
    private ClientService clientService;

    public SentimentService(SentimentRepository sentimentRepository, ClientService clientService) {
        this.sentimentRepository = sentimentRepository;
        this.clientService = clientService;
    }

    public void saveSentiment(Sentiment sentiment) {
        Client clientInBDD = clientService.readOrCreateClient(sentiment.getClient());
        sentiment.setClient(clientInBDD);
        this.sentimentRepository.save(sentiment);
    }

    public List<Sentiment> search(TypeSentiment type, int clientId) {
        if(type == null && clientId == -1) {
            return this.sentimentRepository.findAll();
        }else if (type == null && clientId > 0) {
            return this.sentimentRepository.findByClientId(clientId);
        } else if (type != null && clientId == -1) {
            return this.sentimentRepository.findByType(type);
        }else{
            return this.sentimentRepository.findByTypeAndClientId(type, clientId);
        }

    }

    public void deleteSentimentById(int id) {
        sentimentRepository.deleteById(id);
    }

    public void updateSentiment(int id, Sentiment sentiment) {
        Optional<Sentiment> sentimentINBDD = sentimentRepository.findById(id);
        if(sentimentINBDD.isPresent()){

            if(
                    sentiment.getTexte().contains("pas") || sentiment.getTexte().contains("mauvais")
            ){
                sentimentINBDD.get().setType(TypeSentiment.NEGATIF);
            }else{
                sentimentINBDD.get().setType(TypeSentiment.POSITIF);
            }
            sentimentINBDD.get().setTexte(sentiment.getTexte());
            this.sentimentRepository.save(sentimentINBDD.get());
        }
    }


    public void initializeSentiments(){

        Faker faker = new Faker();

        List<Sentiment> sentiments = IntStream.range(2, 6)
                .mapToObj(clientIndex -> {

                    Client client = Client.builder()
                            .email(faker.lorem().word() + clientIndex + "@gmail.com")
                            // Numéro de téléphone à 8 chiffres
                            .telephone(String.valueOf(faker.number().numberBetween(10000000, 99999999)))
                            .build();

                    // Crée un stream de sentiments pour le client actuel
                    return IntStream.range(2, 5)
                            .mapToObj(sentimentIndex -> {

                                // Sélectionne aléatoirement entre POSITIF et NEGATIF
                                TypeSentiment randomType = faker.options().option(TypeSentiment.class);

                                return Sentiment.builder()
                                        .client(client)
                                        .texte(faker.lorem().sentence())
                                        .type(randomType)
                                        .build();
                            });
                })
                .flatMap(s -> s)
                .collect(Collectors.toList());

        sentimentRepository.saveAll(sentiments);

    }


}
