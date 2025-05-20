package fr.ut1.m2ipm.dafumarket.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Compte {

    @Id
    @Column(length = 100)
    private String login;
    private String password;
    private Timestamp createdAt;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

