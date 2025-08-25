package com.memnik.mapper;

import com.memnik.dao.*;
import com.memnik.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping(target = "memsCount", source = "mems", qualifiedByName = "mapMems")
    @Mapping(target = "jokesCount", source = "jokes", qualifiedByName = "mapJokes")
    @Mapping(target = "postcardsCount", source = "postcards", qualifiedByName = "mapPostcards")
    @Mapping(target = "quotesCount", source = "quotes", qualifiedByName = "mapQuotes")
    @Mapping(target = "videosCount", source = "videos", qualifiedByName = "mapVideos")
    TagDto toDto(TagEntity tagEntity);
    List<TagDto> toDto(List<TagEntity> tagEntities);

    @Named("mapMems")
    default int mapMems(List<MemEntity> mems) {
        return mems.size();
    }
    @Named("mapJokes")
    default int mapJokes(List<JokeEntity> jokes) {
        return jokes.size();
    }
    @Named("mapPostcards")
    default int mapPostcards(List<PostcardEntity> postcards) {
        return postcards.size();
    }
    @Named("mapQuotes")
    default int mapQuotes(List<QuoteEntity> quotes) {
        return quotes.size();
    }
    @Named("mapVideos")
    default int mapVideos(List<VideoEntity> videos) {
        return videos.size();
    }

}
