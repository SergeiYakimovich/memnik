package com.memnik.dao;

import com.memnik.common.constants.Languages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.memnik.common.constants.Constants.ANY_AUTHOR;
import static com.memnik.factory.JokeFactory.getJokeEntity;
import static com.memnik.factory.TagFactory.getTagEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JokeRepositoryTest {
    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MemRepository memRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private PostcardRepository postcardRepository;

    @BeforeEach
    void setUp() {
        jokeRepository.deleteAll();
        memRepository.deleteAll();
        quoteRepository.deleteAll();
        postcardRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    void testRepository() {
        TagEntity tagEntity = getTagEntity();
        TagEntity savedTagEntity = tagRepository.save(tagEntity);
        JokeEntity jokeEntity = getJokeEntity();
        jokeEntity.addTag(savedTagEntity);
        JokeEntity savedJokeEntity = jokeRepository.save(jokeEntity);

        assertEquals(1L, jokeRepository.count());
        assertEquals(1L, tagRepository.count());
        JokeEntity foundJokeEntity = jokeRepository.findById(savedJokeEntity.getId()).get();
        assertEquals(foundJokeEntity.getId(), savedJokeEntity.getId());
        assertEquals(foundJokeEntity.getInformation(), savedJokeEntity.getInformation());
        assertEquals(foundJokeEntity.getTags().size(), 1);
        assertEquals(foundJokeEntity.getTags().get(0).getId(), savedTagEntity.getId());
        assertEquals(foundJokeEntity.getTags().get(0).getAuthor(), savedTagEntity.getAuthor());
    }

    @Test
    void findRandomJokeByUsedFalseTest() {
        JokeEntity jokeEntity1 = getJokeEntity();
        jokeEntity1.setUsed(true);
        jokeRepository.save(jokeEntity1);
        JokeEntity jokeEntity2 = getJokeEntity();
        JokeEntity savedJokeEntity = jokeRepository.save(jokeEntity2);

        Optional<JokeEntity> randomJokeEntity = jokeRepository.findRandomByUsedFalse();
        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity.getId());
    }

    @Test
    void findRandomJokeByLanguageAndUsedFalseTest() {
        JokeEntity jokeEntity1 = getJokeEntity();
        jokeEntity1.setUsed(true);
        jokeRepository.save(jokeEntity1);

        JokeEntity jokeEntity2 = getJokeEntity();
        JokeEntity savedJokeEntity2 = jokeRepository.save(jokeEntity2);

        JokeEntity jokeEntity3 = getJokeEntity();
        jokeEntity3.setLanguage("EN");
        jokeRepository.save(jokeEntity3);

        Optional<JokeEntity> randomJokeEntity = jokeRepository.findRandomByLanguageAndUsedFalse(Languages.RU.name());
        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity2.getId());
    }

    @Test
    void setUsedTrueTest() {
        JokeEntity jokeEntity = getJokeEntity();
        jokeRepository.save(jokeEntity);
        int num = jokeRepository.setUsedTrue(jokeEntity.getId());
        assertEquals(1, num);

        JokeEntity foundJokeEntity = jokeRepository.findById(jokeEntity.getId()).get();
        assertTrue(foundJokeEntity.isUsed());
    }

    @Test
    void findRandomJoke() {
        TagEntity tagEntity = getTagEntity();
        TagEntity savedTagEntity = tagRepository.save(tagEntity);
        JokeEntity jokeEntity = getJokeEntity();
        jokeEntity.addTag(savedTagEntity);
        JokeEntity savedJokeEntity = jokeRepository.save(jokeEntity);

        Optional<JokeEntity> randomJokeEntity = jokeRepository.findRandom(
                savedJokeEntity.getLanguage(),
                List.of(savedTagEntity.getName()),
                savedJokeEntity.getAuthor());

        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity.getId());

        randomJokeEntity = jokeRepository.findRandom(
                Languages.ANY.name(),
                List.of(savedTagEntity.getName()),
                savedJokeEntity.getAuthor());

        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity.getId());

        randomJokeEntity = jokeRepository.findRandom(
                savedJokeEntity.getLanguage(),
                List.of(savedTagEntity.getName()),
                ANY_AUTHOR);

        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity.getId());

        randomJokeEntity = jokeRepository.findRandom(
                savedJokeEntity.getLanguage(),
                null,
                savedJokeEntity.getAuthor());

        assertTrue(randomJokeEntity.isPresent());
        assertEquals(randomJokeEntity.get().getId(), savedJokeEntity.getId());

        randomJokeEntity = jokeRepository.findRandom(
                Languages.EN.name(),
                List.of(savedTagEntity.getName()),
                savedJokeEntity.getAuthor());

        assertTrue(randomJokeEntity.isEmpty());

        randomJokeEntity = jokeRepository.findRandom(
                savedJokeEntity.getLanguage(),
                List.of(savedTagEntity.getName()),
                savedJokeEntity.getAuthor()+"1");

        assertTrue(randomJokeEntity.isEmpty());

        randomJokeEntity = jokeRepository.findRandom(
                savedJokeEntity.getLanguage(),
                List.of(savedTagEntity.getName()+"1"),
                savedJokeEntity.getAuthor());

        assertTrue(randomJokeEntity.isEmpty());
    }
}