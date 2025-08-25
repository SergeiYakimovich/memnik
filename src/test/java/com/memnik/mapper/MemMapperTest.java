package com.memnik.mapper;

import com.memnik.dao.MemEntity;
import com.memnik.dao.TagEntity;
import com.memnik.dto.BaseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.memnik.factory.MemFactory.getMemEntityWithId;
import static com.memnik.factory.TagFactory.getTagEntityWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemMapperTest {
    MemMapper memMapper = new MemMapperImpl();

    @Test
    void toDto() {
        TagEntity tagEntity = getTagEntityWithId();
        MemEntity memEntity = getMemEntityWithId();
        memEntity.addTag(tagEntity);

        List<BaseDto> memDtos = memMapper.toDto(List.of(memEntity));

        assertThat(memDtos.get(0))
                .usingRecursiveComparison()
                .ignoringFields("tags")
                .isEqualTo(memEntity);
        assertEquals(memDtos.get(0).getTags(), List.of(tagEntity.getName()));
    }
}
