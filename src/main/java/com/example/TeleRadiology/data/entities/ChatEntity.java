package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private CredentialsEntity user1Id;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private CredentialsEntity user2Id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportEntity reportId;
}
