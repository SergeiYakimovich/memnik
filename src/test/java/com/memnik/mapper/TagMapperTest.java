package com.memnik.mapper;

import com.memnik.dao.*;
import com.memnik.dto.TagDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.memnik.factory.JokeFactory.getJokeEntityWithId;
import static com.memnik.factory.MemFactory.getMemEntityWithId;
import static com.memnik.factory.PostcardFactory.getPostcardEntityWithId;
import static com.memnik.factory.QuoteFactory.getQuoteEntityWithId;
import static com.memnik.factory.TagFactory.getTagEntityWithId;
import static com.memnik.factory.VideoFactory.getVideoEntityWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagMapperTest {
    TagMapper tagMapper = new TagMapperImpl();

    @Test
    void toDto() {
        MemEntity memEntity = getMemEntityWithId();
        TagEntity tagEntity = getTagEntityWithId();
        memEntity.addTag(tagEntity);
        JokeEntity jokeEntity = getJokeEntityWithId();
        tagEntity.addJoke(jokeEntity);
        PostcardEntity postcardEntity = getPostcardEntityWithId();
        tagEntity.addPostcard(postcardEntity);
        QuoteEntity quoteEntity = getQuoteEntityWithId();
        tagEntity.addQuote(quoteEntity);
        VideoEntity videoEntity = getVideoEntityWithId();
        tagEntity.addVideo(videoEntity);

        List<TagDto> tagDtos = tagMapper.toDto(List.of(tagEntity));

        assertThat(tagDtos.get(0))
                .usingRecursiveComparison()
                .ignoringFields("memsCount", "jokesCount", "postcardsCount",
                        "quotesCount", "videosCount")
                .isEqualTo(tagEntity);
        assertEquals(tagDtos.get(0).getMemsCount(), tagEntity.getMems().size());
        assertEquals(tagDtos.get(0).getJokesCount(), tagEntity.getJokes().size());
        assertEquals(tagDtos.get(0).getPostcardsCount(), tagEntity.getPostcards().size());
        assertEquals(tagDtos.get(0).getQuotesCount(), tagEntity.getQuotes().size());
        assertEquals(tagDtos.get(0).getVideosCount(), tagEntity.getVideos().size());
    }
}
