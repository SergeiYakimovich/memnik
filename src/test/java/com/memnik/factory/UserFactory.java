package com.memnik.factory;

import com.memnik.dao.user.UserEntity;
import com.memnik.dto.LoginDto;
import com.memnik.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.memnik.common.constants.Constants.USER_ROLE;
import static com.memnik.common.constants.Constants.USER_ROLE_TEXT;

public class UserFactory {
    public static final String USER_NAME = "Ivan";
    public static final String USER_EMAIL = "ivan@gmail.com";
    public static final String USER_PASSWORD = "123456";
    public static final Long USER_ID = 5L;
    public static final String USER_CODE = "123456";
    public static final LocalDateTime USER_CREATED = LocalDateTime.now();
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    public static final LoginDto LOGIN_DTO = LoginDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .passwordConfirm(USER_PASSWORD)
                .build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(USER_ID)
            .name(USER_NAME)
            .email(USER_EMAIL)
            .emailConfirmed(false)
            .mailingForbidden(false)
            .email(USER_EMAIL)
            .role(USER_ROLE_TEXT)
            .createdAt(USER_CREATED)
            .language("RU")
            .build();
    public static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(USER_NAME);
        userEntity.setCode(USER_CODE);
        userEntity.setEmail(USER_EMAIL);
        userEntity.setEmailConfirmed(false);
        userEntity.setMailingForbidden(false);
        userEntity.setPassword(PASSWORD_ENCODER.encode(USER_PASSWORD));
        userEntity.setRole(USER_ROLE);
        userEntity.setCreatedAt(USER_CREATED);
        userEntity.setLanguage("RU");
        return userEntity;
    }
    public static UserEntity getUserEntityWithId() {
        UserEntity userEntity = getUserEntity();
        userEntity.setId(USER_ID);
        return userEntity;
    }
    public static final MultiValueMap<String, String> REGISTRATION_PARAMS = CollectionUtils.toMultiValueMap(
            Map.of("name", List.of(USER_NAME),"email", List.of(USER_EMAIL),
                    "language", List.of("RU"),
                    "password", List.of(USER_PASSWORD), "passwordConfirm", List.of(USER_PASSWORD)));
    public static final MultiValueMap<String, String> REGISTRATION_PARAMS_WRONG_RASSWORD = CollectionUtils.toMultiValueMap(
            Map.of("name", List.of(USER_NAME), "email", List.of(USER_EMAIL),
                    "language", List.of("RU"),
                    "password", List.of(USER_PASSWORD), "passwordConfirm", List.of(USER_PASSWORD+"1")));
    public static final MultiValueMap<String, String> REGISTRATION_WRONG_PARAMS = CollectionUtils.toMultiValueMap(
            Map.of("name", List.of(""), "email", List.of(USER_EMAIL),
                    "language", List.of("RU"),
                    "password", List.of("12"), "passwordConfirm", List.of("")));
    public static final MultiValueMap<String, String> USER_ID_PARAMS = CollectionUtils.toMultiValueMap(
            Map.of("userId", List.of("1")));
}
