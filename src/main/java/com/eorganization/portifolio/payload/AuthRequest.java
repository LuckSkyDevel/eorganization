package com.eorganization.portifolio.payload;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

}
