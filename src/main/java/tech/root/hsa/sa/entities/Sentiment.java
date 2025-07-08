package tech.root.hsa.sa.entities;

import jakarta.persistence.*;
import lombok.Builder;
import tech.root.hsa.sa.enums.TypeSentiment;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Builder
@Entity
@Table(name = "sentiments")
public class Sentiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "texte")
    private String texte;
    @Column(name = "type")
    private TypeSentiment type;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Sentiment() {
    }
    public Sentiment(int id, String texte, TypeSentiment type, Client client) {
        this.id = id;
        this.texte = texte;
        this.type = type;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public TypeSentiment getType() {
        return type;
    }

    public void setType(TypeSentiment type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
