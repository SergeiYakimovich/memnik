package com.memnik.service.common;

import com.memnik.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${memnik.application-url}")
    private String applicationUrl;
    @Autowired
    private JavaMailSender mailSender;
    public boolean sendConfirmMail(UserDto userDto) {
        try {
            SimpleMailMessage message = makeConfirmMessage(userDto);
            mailSender.send(message);
            log.info("Sent confirmation letter to %s".formatted(userDto.getEmail()));
            return true;
        } catch (Exception e) {
            log.error("Can't send letter to %s: %s".formatted(userDto.getEmail(), e.getMessage()));
            return false;
        }
    }

    private SimpleMailMessage makeConfirmMessage(UserDto userDto) {
        String url = String.format("http://%s/confirm/%s", applicationUrl, userDto.getCode());
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(mailFrom);
        simpleMail.setTo(userDto.getEmail());
        simpleMail.setSubject("Memnik - registration");
        simpleMail.setText(("""
                Please confirm your email.
                Visit page: %s
                """).formatted(url));

        return simpleMail;
    }

    public boolean sendNotificationMail(UserDto userDto, File file, String text) {
        try {
            MimeMessage message = makeNotificationMessage(userDto, file, text);
            mailSender.send(message);
            log.info("Sent notification letter to %s".formatted(userDto.getEmail()));
            return true;
        } catch (Exception e) {
            log.error("Can't send letter to %s: %s".formatted(userDto.getEmail(), e.getMessage()));
            return false;
        }
    }

    private MimeMessage makeNotificationMessage(UserDto userDto, File file, String text) throws MessagingException {
        String urlMain = String.format("http://%s", applicationUrl);
        String urlUnsubscribe = String.format("http://%s/unsubscribe/%s", applicationUrl, userDto.getCode());
        String body = """
                Hello, %s!
               
               
                %s
               
               
                Enjoy Memnik application!
                Visit page: %s for more mems, jokes, postcards, quotes and videos
                
                
                For unsubscribe visit page: %s
                """.formatted(userDto.getName(), text, urlMain, urlUnsubscribe);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(mailFrom);
        messageHelper.setTo(userDto.getEmail());
        messageHelper.setSubject("Memnik - notification");
        messageHelper.setText(body);
        if (file != null) {
            messageHelper.addInline("", file);
        }

        return mimeMessage;
    }

    public boolean sendPictureToEmail(String email, File file, String userName) {
        try {
            MimeMessage message = makeSendPictureMessage(email, file, userName);
            mailSender.send(message);
            log.info("Sent picture to %s".formatted(email));
            return true;
        } catch (Exception e) {
            log.error("Can't send picture to %s: %s".formatted(email, e.getMessage()));
            return false;
        }
    }

    private MimeMessage makeSendPictureMessage(String email, File file, String userName) throws MessagingException {
        String urlMain = String.format("http://%s", applicationUrl);
        String body = """
                Hello, %s!
                Enjoy picture you liked
                
                Visit page: %s for more mems, jokes, postcards, quotes and videos
                """.formatted(userName, urlMain);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(mailFrom);
        messageHelper.setTo(email);
        messageHelper.setSubject("Memnik - notification");
        messageHelper.setText(body);
        if (file != null) {
            messageHelper.addAttachment(file.getName(), file);
        }

        return mimeMessage;
    }

    public boolean sendVideoToEmail(String email, File file, String userName) {
        try {
            MimeMessage message = makeSendVideoMessage(email, file, userName);
            mailSender.send(message);
            log.info("Sent video to %s".formatted(email));
            return true;
        } catch (Exception e) {
            log.error("Can't send video to %s: %s".formatted(email, e.getMessage()));
            return false;
        }
    }

    private MimeMessage makeSendVideoMessage(String email, File file, String userName) throws MessagingException {
        String urlMain = String.format("http://%s", applicationUrl);
        String body = """
                Hello, %s!
                Enjoy video you liked
                
                Visit page: %s for more mems, jokes, postcards, quotes and videos
                """.formatted(userName, urlMain);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(mailFrom);
        messageHelper.setTo(email);
        messageHelper.setSubject("Memnik - notification");
        messageHelper.setText(body);
        if (file != null) {
            FileSystemResource resource = new FileSystemResource(file);
            messageHelper.addAttachment(file.getName(), resource);
        }

        return mimeMessage;
    }
}
