package com.memnik.service;

import com.memnik.dao.TagEntity;
import com.memnik.dao.TagRepository;
import com.memnik.dto.TagDto;
import com.memnik.factory.TagFactory;
import com.memnik.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.memnik.factory.TagFactory.getTagEntityWithId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private TagService tagService;

    @Test
    void getTagsOkTest() {
        when(tagMapper.toDto((List<TagEntity>) any())).thenReturn(List.of(TagFactory.TAG_DTO));

        List<TagDto> tags = tagService.getTags();

        assertEquals(1, tags.size());
        assertEquals(TagFactory.TAG_DTO.getId(), tags.get(0).getId());

    }

    @Test
    void createTagOkTest() {
        when(tagRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

        boolean result = tagService.createTag("name", "author");

        assertTrue(result);
    }

    @Test
    void createTagFailTest() {
        when(tagRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(getTagEntityWithId()));

        boolean result = tagService.createTag("name", "author");

        assertFalse(result);
    }
}
