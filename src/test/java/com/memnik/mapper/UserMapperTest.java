package com.memnik.mapper;

import com.memnik.dao.user.UserEntity;
import com.memnik.dto.UserDto;
import com.memnik.factory.UserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.memnik.factory.UserFactory.USER_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    UserMapper userMapper = new UserMapperImpl();

    @Test
    void toEntity() {
        UserDto userDto = USER_DTO;
        UserEntity userEntity = userMapper.toEntity(List.of(userDto)).get(0);

        assertThat(userEntity).usingRecursiveComparison()
                .ignoringFields("password", "role")
                .isEqualTo(userDto);
        assertEquals(userDto.getRole(), userEntity.getRole().getName());

    }

    @Test
    void toDto() {
        UserEntity userEntity = UserFactory.getUserEntity();
        UserDto userDto = userMapper.toDto(List.of(userEntity)).get(0);

        assertThat(userEntity).usingRecursiveComparison()
                .ignoringFields("password", "role")
                .isEqualTo(userDto);
        assertEquals(userDto.getRole(), userEntity.getRole().getName());

    }
}