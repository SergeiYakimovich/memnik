package com.memnik.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.memnik.common.constants.Constants.MAX_PASSWORD_LENGTH;
import static com.memnik.common.constants.Constants.MIN_PASSWORD_LENGTH;

@Builder
@Getter
public class LoginDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    private String password;
    @NotBlank
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    private String passwordConfirm;
    @NotBlank
    private String language;
}
