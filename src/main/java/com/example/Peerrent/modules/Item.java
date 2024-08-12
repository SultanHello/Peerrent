package com.example.Peerrent.modules;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String nameItem;
    private String info;
    private Time timeType;
    private int price;
    private int time;
    private int priceInTime;
    private Date startRent;
    private Date endRent;

    @ManyToOne
    @JoinColumn(
            name = "userId"
    )
    private User owner;
    @ManyToOne
    @JoinColumn(
            name="clientId"
    )

    private User client;

}
