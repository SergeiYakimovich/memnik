package com.memnik.factory;

import com.memnik.dao.PostcardEntity;
import com.memnik.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class PostcardFactory {
    public static final Long POSTCARD_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String ADDRESS = "storage\\image0.jpeg";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final String TAG_NAMES = "tags";
    public static final BaseDto POSTCARD_DTO = BaseDto.builder()
            .id(POSTCARD_ID)
            .author(AUTHOR)
            .information(ADDRESS)
            .tags(List.of(TAG_NAMES))
            .createdAt(CREATED_AT)
            .used(false)
            .language("RU")
            .build();

    public static PostcardEntity getPostcardEntity() {
        PostcardEntity postcardEntity = new PostcardEntity();
        postcardEntity.setAuthor(AUTHOR);
        postcardEntity.setInformation(ADDRESS);
        postcardEntity.setCreatedAt(CREATED_AT);
        postcardEntity.setUsed(false);
        postcardEntity.setLanguage("RU");
        return postcardEntity;
    }
    public static PostcardEntity getPostcardEntityWithId() {
        PostcardEntity postcardEntity = getPostcardEntity();
        postcardEntity.setId(POSTCARD_ID);
        return postcardEntity;
    }
}
