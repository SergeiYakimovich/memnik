package com.memnik.mapper;

import com.memnik.dao.user.Role;
import com.memnik.dao.user.UserEntity;
import com.memnik.dto.LoginDto;
import com.memnik.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.memnik.common.constants.Constants.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mapping(target = "role", source = "role", qualifiedByName = "mapToRole")
    UserEntity toEntity(UserDto userDto);
    List<UserEntity> toEntity(List<UserDto> userDtos);

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toLocalDateTime")
    @Mapping(target = "lastLogin", source = "lastLogin", qualifiedByName = "toLocalDateTime")
    UserDto toDto(UserEntity userEntity);
    List<UserDto> toDto(List<UserEntity> userEntities);

    @Named("mapToRole")
    default Role mapToRole(String value) {
        if(value == null) {
            return null;
        }
        return ROLE_MAP.get(value);
    }

    @Named("toLocalDateTime")
    default LocalDateTime toLocalDateTime(LocalDateTime localDateTime) {
        if(localDateTime != null) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.MINUTES);
        }
        return localDateTime;
    }

    default UserEntity makeUserFromLoginDto(LoginDto loginDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(loginDto.getName());
        userEntity.setCode(UUID.randomUUID().toString());
        userEntity.setEmail(loginDto.getEmail());
        userEntity.setEmailConfirmed(false);
        userEntity.setMailingForbidden(false);
        userEntity.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        userEntity.setRole(USER_ROLE);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setLanguage(loginDto.getLanguage());
        return userEntity;
    }
}
