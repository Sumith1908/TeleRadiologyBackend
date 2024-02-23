package com.example.TeleRadiology.domain.model;

import lombok.Data;

@Data
public class Credentials {

    private int id;

    private String email;

    private String password;

    private int active;

    private int role;

}
