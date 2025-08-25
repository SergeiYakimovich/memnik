package com.memnik.dao;

import com.memnik.factory.JokeFactory;
import com.memnik.factory.MemFactory;
import com.memnik.factory.TagFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MemRepository memRepository;
    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private PostcardRepository postcardRepository;
    @Autowired
    private QuoteRepository quoteRepository;

    @BeforeEach
    void setUp() {
        memRepository.deleteAll();
        jokeRepository.deleteAll();
        postcardRepository.deleteAll();
        quoteRepository.deleteAll();
        tagRepository.deleteAll();
    }
    @Test
    void testRepository() {
        MemEntity memEntity = MemFactory.getMemEntity();
        MemEntity savedMemEntity = memRepository.save(memEntity);
        JokeEntity jokeEntity = jokeRepository.save(JokeFactory.getJokeEntity());
        JokeEntity savedJokeEntity = jokeRepository.save(jokeEntity);
        TagEntity tagEntity = TagFactory.getTagEntity();
        tagEntity.addMem(savedMemEntity);
        tagEntity.addJoke(savedJokeEntity);
        TagEntity savedTagEntity = tagRepository.save(tagEntity);

        assertEquals(1L, tagRepository.count());
        assertEquals(1L, memRepository.count());
        assertEquals(1L, jokeRepository.count());
        TagEntity foundTagEntity = tagRepository.findByNameIgnoreCase(savedTagEntity.getName().toUpperCase()).get();
        assertEquals(foundTagEntity.getId(), savedTagEntity.getId());
        assertEquals(foundTagEntity.getAuthor(), savedTagEntity.getAuthor());
        assertEquals(foundTagEntity.getMems().size(), 1);
        assertEquals(foundTagEntity.getMems().get(0).getId(), savedMemEntity.getId());
        assertEquals(foundTagEntity.getMems().get(0).getAuthor(), savedMemEntity.getAuthor());
        assertEquals(foundTagEntity.getJokes().size(), 1);
        assertEquals(foundTagEntity.getJokes().get(0).getInformation(), savedJokeEntity.getInformation());
        assertEquals(foundTagEntity.getJokes().get(0).getId(), savedJokeEntity.getId());
    }
}
