package com.memnik.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TagDto {
    private Long id;
    private String author;
    private String name;
    private LocalDateTime createdAt;
    private int memsCount;
    private int jokesCount;
    private int postcardsCount;
    private int quotesCount;
    private int videosCount;
}
