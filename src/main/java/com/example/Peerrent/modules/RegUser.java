package com.example.Peerrent.modules;

import lombok.Data;

@Data
public class RegUser {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String city;
}
