package com.memnik.factory;

import com.memnik.dao.MemEntity;
import com.memnik.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class MemFactory {
    public static final Long MEM_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String ADDRESS = "storage\\image0.jpeg";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final String TAG_NAMES = "tags";
    public static final BaseDto MEM_DTO = BaseDto.builder()
            .id(MEM_ID)
            .author(AUTHOR)
            .information(ADDRESS)
            .tags(List.of(TAG_NAMES))
            .createdAt(CREATED_AT)
            .used(false)
            .language("RU")
            .build();

    public static MemEntity getMemEntity() {
        MemEntity memEntity = new MemEntity();
        memEntity.setAuthor(AUTHOR);
        memEntity.setInformation(ADDRESS);
        memEntity.setCreatedAt(CREATED_AT);
        memEntity.setUsed(false);
        memEntity.setLanguage("RU");
        return memEntity;
    }
    public static MemEntity getMemEntityWithId() {
        MemEntity memEntity = getMemEntity();
        memEntity.setId(MEM_ID);
        return memEntity;
    }
}
