package com.example.Peerrent.repositories;

import com.example.Peerrent.modules.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public Item findByNameItem(String nameItem);


}
