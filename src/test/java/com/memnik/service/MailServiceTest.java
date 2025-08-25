package com.memnik.service;

import com.memnik.service.common.MailService;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import java.io.File;

import static com.memnik.factory.UserFactory.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MailService.class)
@TestPropertySource(properties = {"spring.mail.username:memmik@mail.ru"})
class MailServiceTest {
    @MockBean
    private JavaMailSender mailSender;
    @Autowired
    private MailService mailService;
    @Test
    void sendConfirmMailOkTest() {
        boolean result = mailService.sendConfirmMail(USER_DTO);

        assertTrue(result);
    }
    @Test
    void sendConfirmMailFailTest() {
        doThrow(RuntimeException.class).when(mailSender).send(any(SimpleMailMessage.class));

        boolean result = mailService.sendConfirmMail(USER_DTO);

        assertFalse(result);
    }

    @Test
    void sendNotificationMailOkTest() {
        File file = new File("mems/rose.bmp");
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        boolean result = mailService.sendNotificationMail(USER_DTO, file, "text");

        assertTrue(result);
    }

}
