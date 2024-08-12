package com.example.Peerrent.services;

import com.example.Peerrent.modules.DTO.ItemDTO;
import com.example.Peerrent.modules.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertService {
    public List<ItemDTO> ListToDtoList(List<Item> items){
        List<ItemDTO> dtos =new ArrayList<>();
        for(int i = 0;i<items.size();i++){
            Item oneItem=items.get(i);
            dtos.add(ItemDTO.builder()
                            .nameItem(oneItem.getNameItem())
                            .priceInTime(oneItem.getPriceInTime())
                            .info(oneItem.getInfo())
                            .timeType(oneItem.getTimeType())
                            .time(oneItem.getTime())
                            .price(oneItem.getPrice())
                            .owner(oneItem.getOwner().getName()+ " "+ oneItem.getOwner().getSurname())

                    .build());


        }
        return dtos;

    }
}
