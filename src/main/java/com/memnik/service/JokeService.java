package com.memnik.service;

import com.memnik.dao.JokeEntity;
import com.memnik.dao.TagEntity;
import com.memnik.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class JokeService extends BaseService<JokeEntity> {
    public boolean deleteJoke(Long jokeId) {
        Optional<JokeEntity> optionalJoke = repository.findById(jokeId);
        if(optionalJoke.isEmpty()) {
            log.error("Can't find mem with id %d".formatted(jokeId));
            return false;
        }
        JokeEntity joke = optionalJoke.get();

        for (TagEntity tag : new ArrayList<>(joke.getTags())) {
            tag.removeJoke(joke);
        }

        repository.delete(joke);
        log.info("Joke %d has been deleted".formatted(jokeId));

        return true;
    }
}
