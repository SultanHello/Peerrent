package com.example.Peerrent.modules;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NotId;
    private String message;
    @ManyToOne
    @JoinColumn(
            name="userId"
    )
    private User user;
}
