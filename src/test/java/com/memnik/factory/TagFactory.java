package com.memnik.factory;

import com.memnik.dao.TagEntity;
import com.memnik.dto.TagDto;

import java.time.LocalDateTime;

public class TagFactory {
    public static final Long TAG_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String NAME = "name";
    public static final int MEMS_COUNT = 1;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final TagDto TAG_DTO = TagDto.builder()
            .id(TAG_ID)
            .author(AUTHOR)
            .name(NAME)
            .memsCount(MEMS_COUNT)
            .jokesCount(2)
            .postcardsCount(3)
            .quotesCount(4)
            .videosCount(5)
            .createdAt(CREATED_AT)
            .build();
    public static TagEntity getTagEntity() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setAuthor(AUTHOR);
        tagEntity.setName(NAME);
        tagEntity.setCreatedAt(CREATED_AT);
        return tagEntity;
    }
    public static TagEntity getTagEntityWithId() {
        TagEntity tagEntity = getTagEntity();
        tagEntity.setId(TAG_ID);
        return tagEntity;
    }
}
