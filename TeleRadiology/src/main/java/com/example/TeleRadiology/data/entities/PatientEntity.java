package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private CredentialsEntity userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "current_medication")
    private String currentMedication;

    @Column(name = "past_medication")
    private String pastMedication;

    @Column(name = "chronic_diseases")
    private String chronicDiseases;

    @Column(name = "smoking_habits")
    private String smokingHabits;

    @Column(name = "drinking_habits")
    private String drinkingHabits;

    @Column(name = "food_preferences")
    private String foodPreferences;
}
