package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Annotations")
public class AnnotatedImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "chatId", nullable = false)
    private ChatEntity chatId;

}
