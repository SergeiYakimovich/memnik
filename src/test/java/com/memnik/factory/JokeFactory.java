package com.memnik.factory;

import com.memnik.dao.JokeEntity;
import com.memnik.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class JokeFactory {
    public static final Long JOKE_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String TEXT = "text";
    public static final String LANGUAGE = "RU";
    public static final String TAG_NAMES = "tags";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final BaseDto JOKE_DTO = BaseDto.builder()
            .id(JOKE_ID)
            .author(AUTHOR)
            .information(TEXT)
            .tags(List.of(TAG_NAMES))
            .createdAt(CREATED_AT)
            .used(false)
            .language(LANGUAGE)
            .build();

    public static JokeEntity getJokeEntity() {
        JokeEntity jokeEntity = new JokeEntity();
        jokeEntity.setAuthor(AUTHOR);
        jokeEntity.setInformation(TEXT);
        jokeEntity.setCreatedAt(CREATED_AT);
        jokeEntity.setUsed(false);
        jokeEntity.setLanguage(LANGUAGE);
        return jokeEntity;
    }

    public static JokeEntity getJokeEntityWithId() {
        JokeEntity jokeEntity = getJokeEntity();
        jokeEntity.setId(JOKE_ID);
        return jokeEntity;
    }
}
