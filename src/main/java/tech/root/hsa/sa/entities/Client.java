package tech.root.hsa.sa.entities;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    public Client() {
    }

    public Client(int id, String email, String telephone) {
        this.id = id;
        this.email = email;
        this.telephone = telephone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

