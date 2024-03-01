package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "lab")
public class LabEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private CredentialsEntity userId;

    @Column(name = "rating")
    private double rating;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}
