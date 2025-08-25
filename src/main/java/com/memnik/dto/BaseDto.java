package com.memnik.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BaseDto {
    private Long id;
    private String author;
    private String information;
    private List<String> tags;
    private LocalDateTime createdAt;
    private boolean used;
    private String language;
}
