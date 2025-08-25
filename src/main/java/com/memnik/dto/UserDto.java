package com.memnik.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String code;
    private String email;
    private boolean emailConfirmed;
    private boolean mailingForbidden;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String language;
}
