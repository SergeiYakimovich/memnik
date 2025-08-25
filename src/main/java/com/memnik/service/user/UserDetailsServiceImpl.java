package com.memnik.service.user;

import com.memnik.common.LanguageResolver;
import com.memnik.common.constants.Languages;
import com.memnik.dao.user.UserEntity;
import com.memnik.dao.user.UserRepository;
import com.memnik.dto.LoginDto;
import com.memnik.dto.UserDto;
import com.memnik.mapper.UserMapper;
import com.memnik.service.common.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private LanguageResolver languageResolver;

    @Override
    public UserEntity loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByNameIgnoreCase(name);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    public boolean createUser(LoginDto loginDto) {
        Optional<UserEntity> userFromDB = userRepository.findByNameIgnoreCase(loginDto.getName());
        if (userFromDB.isPresent()) {
            log.info("User with name %s already exists".formatted(loginDto.getName()));
            return false;
        }
        UserEntity userEntity = userMapper.makeUserFromLoginDto(loginDto);
        userRepository.save(userEntity);
        log.info("User %s has been created".formatted(loginDto.getName()));
        return true;
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(UserEntity::getId))
                .map(userMapper::toDto).toList();
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            log.info("User %d has been deleted".formatted(userId));
            return true;
        }
        return false;
    }

    public boolean confirmUserEmail(String code) {
        Optional<UserEntity> user = userRepository.findByCode(code);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setEmailConfirmed(true);
            userRepository.save(userEntity);
            log.info("User %s has been confirmed".formatted(userEntity.getName()));
            languageResolver.setCurrentLanguage(userEntity.getLanguage());
            return true;
        } else {
            log.error("Can't find and confirm user with code %s".formatted(code));
            return false;
        }
    }

    public boolean unsubscribeUser(String code) {
        Optional<UserEntity> user = userRepository.findByCode(code);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setMailingForbidden(true);
            userRepository.save(userEntity);
            log.info("User %s has been unsubscribed".formatted(userEntity.getName()));
            languageResolver.setCurrentLanguage(userEntity.getLanguage());
            return true;
        } else {
            log.error("Can't find and unsubscribe user with code %s".formatted(code));
            return false;
        }
    }

    public void updateLastLogin(UserEntity user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean sendConfirmMail(String name) {
        UserDto userDto = userMapper.toDto(loadUserByUsername(name));
        return mailService.sendConfirmMail(userDto);
    }

    public int deleteUnconfirmedUsers() {
        int num = userRepository.deleteAllByEmailConfirmedFalseAndCreatedAtBefore(LocalDateTime.now().minusHours(1));
        log.info("Deleted %d unconfirmed users".formatted(num));
        return num;
    }

    public List<UserDto> findUsersForNotification(Languages language) {
        List<UserEntity> userEntities = userRepository.findAllByMailingForbiddenFalseAndLanguage(language.name());

        return userMapper.toDto(userEntities);
    }

}
