package com.memnik.factory;

import com.memnik.dao.MemEntity;
import com.memnik.dao.VideoEntity;
import com.memnik.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class VideoFactory {
    public static final Long VIDEO_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String ADDRESS = "storage\\image0.jpeg";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final String TAG_NAMES = "tags";
    public static final BaseDto VIDEO_DTO = BaseDto.builder()
            .id(VIDEO_ID)
            .author(AUTHOR)
            .information(ADDRESS)
            .tags(List.of(TAG_NAMES))
            .createdAt(CREATED_AT)
            .used(false)
            .language("RU")
            .build();

    public static VideoEntity getVideoEntity() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setAuthor(AUTHOR);
        videoEntity.setInformation(ADDRESS);
        videoEntity.setCreatedAt(CREATED_AT);
        videoEntity.setUsed(false);
        videoEntity.setLanguage("RU");
        return videoEntity;
    }
    public static VideoEntity getVideoEntityWithId() {
        VideoEntity videoEntity = getVideoEntity();
        videoEntity.setId(VIDEO_ID);
        return videoEntity;
    }
}
