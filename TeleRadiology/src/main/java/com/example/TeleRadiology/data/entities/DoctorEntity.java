package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "doctor")
public class DoctorEntity {
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

    @Column(name = "gender")
    private String gender;

    @Column(name = "hospital_address", nullable = false)
    private String hospitalAddress;

    @Column(name = "hospital_city", nullable = false)
    private String hospitalCity;

    @Column(name = "hospital_state")
    private String hospitalState;

    @Column(name = "hospital_pin_code")
    private String hospitalPinCode;

    @Column(name = "hospital_email", nullable = false)
    private String hospitalEmail;

    @Column(name = "hospital_phone_number", nullable = false)
    private String hospitalPhoneNumber;

    @Column(name = "experience")
    private int experience;

    @Column(name = "rating")
    private double rating;

    @Column(name = "highest_education", nullable = false)
    private String highestEducation;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "profile_photo")
    private String profilePhoto;

}
