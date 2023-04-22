package com.example.bank.models;

import jakarta.persistence.*;

@Entity(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 25, unique = true)
    private String roleName;

    public Role() {

    }

    public Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
