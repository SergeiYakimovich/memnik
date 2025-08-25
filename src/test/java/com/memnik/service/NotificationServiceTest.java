package com.memnik.service;

import com.memnik.common.constants.Languages;
import com.memnik.mapper.UserMapperImpl;
import com.memnik.service.common.MailService;
import com.memnik.service.common.NotificationService;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.memnik.factory.JokeFactory.JOKE_DTO;
import static com.memnik.factory.MemFactory.MEM_DTO;
import static com.memnik.factory.UserFactory.USER_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private MemService memService;
    @Mock
    private JokeService jokeService;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private MailService mailService;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void notifyUsersOkTest() {
        // given
        when(userDetailsService.findUsersForNotification(any())).thenReturn(List.of(USER_DTO));
        when(memService.findRandomForNotification(Languages.ANY)).thenReturn(Optional.of(MEM_DTO));
        when(memService.findRandomForNotification(Languages.RU)).thenReturn(Optional.of(MEM_DTO));
        when(memService.findRandomForNotification(Languages.EN)).thenReturn(Optional.empty());
        when(jokeService.findRandomForNotification(Languages.ANY)).thenReturn(Optional.of(JOKE_DTO));
        when(jokeService.findRandomForNotification(Languages.RU)).thenReturn(Optional.of(JOKE_DTO));
        when(jokeService.findRandomForNotification(Languages.EN)).thenReturn(Optional.empty());
        // when
        notificationService.notifyUsers();
        // then
        verify((userDetailsService), times(3)).findUsersForNotification(any());
        verify(memService, times(3)).findRandomForNotification(any());
        verify(jokeService, times(3)).findRandomForNotification(any());
        verify(mailService, times(2)).sendNotificationMail(any(), any(), any());
    }
}
