package com.example.Peerrent.controllers;


import com.example.Peerrent.modules.DTO.ItemDTO;
import com.example.Peerrent.modules.RegUser;
import com.example.Peerrent.services.AppService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;

@RestController
@AllArgsConstructor
public class AppController {
    private AppService appService;

    @PostMapping("/register")
    public String reg(@RequestBody RegUser regUser){
        return appService.regUser(regUser);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello from : "+ appService.getName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @PostMapping("/addItem")
    public ItemDTO addItem(@RequestBody ItemDTO itemDto){
        return appService.addItem(itemDto);
    }

    @GetMapping("/items")
    public List<ItemDTO> getLists(){
        return appService.lists();

    }

    @PutMapping("/rent/{id}")
    public String rentItem(@PathVariable Long id){
        return appService.rentItem(id);
    }


}
