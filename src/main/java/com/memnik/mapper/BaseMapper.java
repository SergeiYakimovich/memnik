package com.memnik.mapper;

import com.memnik.dao.TagEntity;
import com.memnik.dto.BaseDto;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<T> {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    BaseDto toDto(T entity);

    List<BaseDto> toDto(List<T> entities);

    @Named("mapTags")
    default List<String> mapTags(List<TagEntity> tags) {
        return tags.stream().map(TagEntity::getName).collect(Collectors.toList());
    }
}
