package com.example.empre_go.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String senha;
    private String role;
}