package com.memnik.service;

import com.memnik.common.LanguageResolver;
import com.memnik.dao.user.UserEntity;
import com.memnik.dao.user.UserRepository;
import com.memnik.dto.UserDto;
import com.memnik.factory.UserFactory;
import com.memnik.mapper.UserMapperImpl;
import com.memnik.service.common.MailService;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.memnik.factory.UserFactory.LOGIN_DTO;
import static com.memnik.factory.UserFactory.USER_DTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MailService mailService;
    @Mock
    private MemService memService;
    @Mock
    private JokeService jokeService;
    @Mock
    private LanguageResolver languageResolver;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void loadUserByUsernameFailTest() {
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername("name"));
    }

    @Test
    void createUserOkTest() {
        // given
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(UserFactory.getUserEntityWithId());

        // when
        boolean result = userDetailsServiceImpl.createUser(LOGIN_DTO);

        // then
        assertTrue(result);
    }

    @Test
    void createUserFailTest() {
        // given
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(UserFactory.getUserEntity()));

        // when
        boolean result = userDetailsServiceImpl.createUser(LOGIN_DTO);

        // then
        assertFalse(result);
    }

    @Test
    void allUsersOkTest() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(UserFactory.getUserEntity()));
        when(userMapper.toDto(anyList())).thenReturn(List.of(UserFactory.USER_DTO));

        // when
        List<UserDto> result = userDetailsServiceImpl.findAllUsers();

        // then
        assertEquals(1, result.size());
        assertEquals(USER_DTO.getId(), result.get(0).getId());
    }

    @Test
    void deleteUserFailTest() {
        // given
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // when
        boolean result = userDetailsServiceImpl.deleteUser(1L);

        // then
        assertFalse(result);
    }

    @Test
    void confirmEmailOkTest() {
        // given
        when(userRepository.findByCode(any())).thenReturn(Optional.of(UserFactory.getUserEntity()));

        // when
        boolean result = userDetailsServiceImpl.confirmUserEmail("123");

        // then
        assertTrue(result);
    }

    @Test
    void unsubscribeOkTest() {
        // given
        when(userRepository.findByCode(any())).thenReturn(Optional.of(UserFactory.getUserEntity()));

        // when
        boolean result = userDetailsServiceImpl.unsubscribeUser("123");

        // then
        assertTrue(result);
    }

    @Test
    void updateLastLoginOkTest() {
        // given
        UserEntity userEntity = UserFactory.getUserEntity();
        userEntity.setLastLogin(null);
        // when
        userDetailsServiceImpl.updateLastLogin(userEntity);

        // then
        assertNotNull(userEntity.getLastLogin());
    }

    @Test
    void sendConfirmMailOkTest() {
        // given
        UserEntity userEntity = UserFactory.getUserEntity();
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(userEntity));
        when(mailService.sendConfirmMail(any())).thenReturn(true);
        // when
        boolean result = userDetailsServiceImpl.sendConfirmMail(userEntity.getName());

        // then
        assertTrue(result);
    }

    @Test
    void deleteUnconfirmedUsersOkTest() {
        // given
        when(userRepository.deleteAllByEmailConfirmedFalseAndCreatedAtBefore(any())).thenReturn(1);
        // when
        int result = userDetailsServiceImpl.deleteUnconfirmedUsers();
        // then
        assertEquals(1, result);
    }

}