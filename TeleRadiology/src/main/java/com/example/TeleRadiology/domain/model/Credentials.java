package com.example.TeleRadiology.domain.model;

import lombok.Data;

@Data
public class Credentials {

    private int id = -1;

    private String email = "";

    private String password = "";

    private int active = 0;

    private String role = "";

}
