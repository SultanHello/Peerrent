package com.example.Peerrent.modules.DTO;

import com.example.Peerrent.modules.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String nameItem;
    private String info;
    private Time timeType;
    private int priceInTime;
    private int price;
    private int time;
    private String owner;

}
