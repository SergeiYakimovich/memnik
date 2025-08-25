package com.memnik.common;

import com.memnik.common.constants.Languages;
import com.memnik.dao.user.UserEntity;
import com.memnik.mapper.UserMapper;
import com.memnik.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationSuccessListenerImpl implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private LanguageResolver languageResolver;
    @Autowired
    private CurrentUserResolver currentUserResolver;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        if (userDetails instanceof UserEntity user) {
            userDetailsService.updateLastLogin(user);
            if (user.getLanguage().equals(Languages.RU.name())) {
                languageResolver.setCurrentLanguage(Languages.RU.name());
            } else {
                languageResolver.setCurrentLanguage(Languages.EN.name());
            }
            currentUserResolver.setCurrentUser(userMapper.toDto(user));
            log.info("User %s login successfully".formatted(user.getName()));
        }
    }
}
