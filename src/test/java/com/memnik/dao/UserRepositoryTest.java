package com.memnik.dao;

import com.memnik.common.constants.Languages;
import com.memnik.dao.user.UserEntity;
import com.memnik.dao.user.UserRepository;
import com.memnik.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRepository() {
        UserEntity userEntity = UserFactory.getUserEntity();
        UserEntity savedEntity = userRepository.save(userEntity);
        UserEntity foundEntityByName = userRepository.findByNameIgnoreCase(userEntity.getName().toUpperCase()).get();
        UserEntity foundEntityByCode = userRepository.findByCode(userEntity.getCode()).get();

        assertThat(userEntity).usingRecursiveComparison().isEqualTo(savedEntity);
        assertThat(savedEntity).usingRecursiveComparison().isEqualTo(foundEntityByName);
        assertThat(foundEntityByName).usingRecursiveComparison().isEqualTo(foundEntityByCode);
    }

    @Test
    void testDeleteAllByEmailConfirmedFalseAndCreatedAtBefore() {
        UserEntity userEntity1 = UserFactory.getUserEntity();
        userEntity1.setCreatedAt(LocalDateTime.now().minusHours(2));
        userRepository.save(userEntity1);

        UserEntity userEntity2 = UserFactory.getUserEntity();
        userEntity2.setName(userEntity2.getName() + "2");
        userEntity2.setCode(userEntity2.getCode() + "2");
        userEntity2.setCreatedAt(LocalDateTime.now().minusHours(2));
        userEntity2.setEmailConfirmed(true);
        userRepository.save(userEntity2);

        UserEntity userEntity3 = UserFactory.getUserEntity();
        userEntity3.setName(userEntity3.getName() + "3");
        userEntity3.setCode(userEntity3.getCode() + "3");
        userEntity3.setCreatedAt(LocalDateTime.now().minusMinutes(30));
        userRepository.save(userEntity3);

        int num = userRepository.deleteAllByEmailConfirmedFalseAndCreatedAtBefore(LocalDateTime.now().minusHours(1));
        assertEquals(num,1);
        List<UserEntity> all = userRepository.findAll();
        assertEquals(2, all.size());

        UserEntity userEntity = userRepository.findByNameIgnoreCase(userEntity2.getName()).get();
        assertEquals(userEntity2.getId(), userEntity.getId());

        userEntity = userRepository.findByNameIgnoreCase(userEntity3.getName()).get();
        assertEquals(userEntity3.getId(), userEntity.getId());
    }

    @Test
    void testFindAllByMailingForbiddenFalse() {
        UserEntity userEntity1 = UserFactory.getUserEntity();
        userRepository.save(userEntity1);

        UserEntity userEntity2 = UserFactory.getUserEntity();
        userEntity2.setName(userEntity2.getName() + "2");
        userEntity2.setCode(userEntity2.getCode() + "2");
        userEntity2.setMailingForbidden(true);
        userRepository.save(userEntity2);

        List<UserEntity> all = userRepository.findAllByMailingForbiddenFalse();
        assertEquals(1, all.size());
        assertEquals(userEntity1.getName(), all.get(0).getName());
    }

    @Test
    void findAllByMailingForbiddenFalseAndLanguage() {
        UserEntity userEntity1 = UserFactory.getUserEntity();
        userRepository.save(userEntity1);

        UserEntity userEntity2 = UserFactory.getUserEntity();
        userEntity2.setName(userEntity2.getName() + "2");
        userEntity2.setCode(userEntity2.getCode() + "2");
        userEntity2.setMailingForbidden(true);
        userRepository.save(userEntity2);

        UserEntity userEntity3 = UserFactory.getUserEntity();
        userEntity3.setName(userEntity3.getName() + "3");
        userEntity3.setCode(userEntity3.getCode() + "3");
        userEntity3.setLanguage("EN");
        userRepository.save(userEntity3);

        List<UserEntity> all = userRepository.findAllByMailingForbiddenFalseAndLanguage(Languages.RU.name());
        assertEquals(1, all.size());
        assertEquals(userEntity1.getName(), all.get(0).getName());
    }
}