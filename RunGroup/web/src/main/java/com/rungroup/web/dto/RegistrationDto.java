package com.rungroup.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDto {
//    private Long id; checking if I really need this
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
