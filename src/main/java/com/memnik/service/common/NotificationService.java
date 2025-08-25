package com.memnik.service.common;

import com.memnik.common.CommonUtils;
import com.memnik.common.constants.Languages;
import com.memnik.dto.BaseDto;
import com.memnik.dto.UserDto;
import com.memnik.mapper.UserMapper;
import com.memnik.service.JokeService;
import com.memnik.service.MemService;
import com.memnik.service.common.MailService;
import com.memnik.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MemService memService;
    @Autowired
    private JokeService jokeService;
    public void notifyUsers() {
        for (Languages language : Languages.values()) {
            List<UserDto> userDtos = userDetailsService.findUsersForNotification(language);
            if(userDtos.isEmpty()) {
                log.info("No users to send notifications for language %s".formatted(language.name()));
            } else {
                Optional<BaseDto> jokeDto = jokeService.findRandomForNotification(language);
                String text = jokeDto.map(x -> "New joke for you: %s".formatted(x.getInformation())).orElse("");
                Optional<BaseDto> memDto = memService.findRandomForNotification(language);
                File file = memDto.map(BaseDto::getInformation).map(CommonUtils::getFileFromAddress).orElse(null);
                if (file == null && text.isEmpty()) {
                    log.info("No random mem or joke to send for language %s".formatted(language.name()));
                    continue;
                }
                log.info("Find for notification for language=%s memId=%s, jokeId=%s".formatted(language.name(),
                        memDto.map(BaseDto::getId).map(Object::toString).orElse("Not found"),
                        jokeDto.map(BaseDto::getId).map(Object::toString).orElse("Not found")));
                int count = 0;
                for (UserDto userDto : userDtos) {
                    if(mailService.sendNotificationMail(userDto, file, text)) {
                        count++;
                    }
                }
                log.info("Sent notification mails to %d users from %d with language %s".formatted(count, userDtos.size(), language.name()));
//                if(count > 0 && file != null) {
//                    memService.setUsedTrue(memDto.get().getId());
//                }
//                if(count > 0 && jokeDto.isPresent()) {
//                    jokeService.setUsedTrue(jokeDto.get().getId());
//                }
            }
        }
    }


}
