package tech.root.hsa.sa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tech.root.hsa.sa.entities.Client;
import tech.root.hsa.sa.entities.Sentiment;
import tech.root.hsa.sa.enums.TypeSentiment;

import java.util.List;

public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {
    public List<Sentiment> findByType(TypeSentiment type);

    List<Sentiment> findByClientId(int clientId);
    List<Sentiment> findByTypeAndClientId(TypeSentiment type, int clientId);
}
