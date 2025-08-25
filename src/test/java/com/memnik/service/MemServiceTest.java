package com.memnik.service;

import com.memnik.common.constants.Languages;
import com.memnik.dao.MemEntity;
import com.memnik.dao.MemRepository;
import com.memnik.dao.TagRepository;
import com.memnik.dto.BaseDto;
import com.memnik.mapper.MemMapper;
import com.memnik.service.common.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.memnik.factory.MemFactory.MEM_DTO;
import static com.memnik.factory.MemFactory.getMemEntityWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {MemService.class})
class MemServiceTest {
    @MockBean
    private MemRepository memRepository;
    @MockBean
    private MemMapper memMapper;
    @MockBean
    private TagRepository tagRepository;
    @MockBean
    private MailService mailService;
    @InjectMocks
    private MemService memService;

    @Test
    void findRandomMemAny() {
        MemEntity memEntity = getMemEntityWithId();
        when(memRepository.findRandomByUsedFalse()).thenReturn(Optional.of(memEntity));
        when(memMapper.toDto(memEntity)).thenReturn(MEM_DTO);

        Optional<BaseDto> result = memService.findRandomForNotification(Languages.ANY);

        assertTrue(result.isPresent());
        assertEquals(MEM_DTO, result.get());
    }

    @Test
    void findRandomMemRu() {
        MemEntity memEntity = getMemEntityWithId();
        when(memRepository.findRandomByLanguageAndUsedFalse(Languages.RU.name())).thenReturn(Optional.of(memEntity));
        when(memMapper.toDto(memEntity)).thenReturn(MEM_DTO);

        Optional<BaseDto> result = memService.findRandomForNotification(Languages.RU);

        assertTrue(result.isPresent());
        assertEquals(MEM_DTO, result.get());
    }

    @Test
    void setUsed() {
        when(memRepository.setUsedTrue(1L)).thenReturn(1);
        int result = memService.setUsedTrue(1L);
        assertEquals(1, result);
    }
}