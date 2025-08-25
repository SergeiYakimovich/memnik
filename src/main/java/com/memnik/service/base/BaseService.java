package com.memnik.service.base;

import com.memnik.common.constants.Languages;
import com.memnik.dao.base.BaseEntity;
import com.memnik.dao.base.BaseRepository;
import com.memnik.dao.TagEntity;
import com.memnik.dao.TagRepository;
import com.memnik.dto.BaseDto;
import com.memnik.mapper.BaseMapper;
import com.memnik.service.common.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getFileFromFullAddress;
import static com.memnik.common.CommonUtils.getNewFilePath;

@Slf4j
@Transactional
public abstract class BaseService<T extends BaseEntity> {
    @Autowired
    protected BaseRepository<T> repository;
    @Autowired
    private BaseMapper<T> mapper;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MailService mailService;

    public Optional<BaseDto> findRandomForNotification(Languages language) {
        Optional<T> entity;
        if (language == Languages.ANY) {
            entity = repository.findRandomByUsedFalse();
        } else {
            entity = repository.findRandomByLanguageAndUsedFalse(language.name());
        }

        if (entity.isEmpty()) {
            log.info("Can't find random entity for language %s".formatted(language.name()));
            return Optional.empty();
        } else {
            return entity.map(mapper::toDto);
        }
    }

    public int setUsedTrue(long id) {
        return repository.setUsedTrue(id);
    }

    public Optional<BaseDto> findNew(String language, List<String> tags, String author) {
        return repository.findRandom(language, tags, author)
                .map(mapper::toDto);
    }

    public void addNew(String language, String author, List<String> tags, String text, T entity) {
        entity.setAuthor(author);
        entity.setInformation(text);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUsed(false);
        entity.setLanguage(language);
        for (String tagName : tags) {
            Optional<TagEntity> tagEntity = tagRepository.findByNameIgnoreCase(tagName);
            tagEntity.ifPresent(entity::addTag);
        }
        repository.save(entity);
        log.info("New instance of %s was added".formatted(entity.getClass().getSimpleName()));
    }

    public void addNewFile(String language, String author, List<String> tags, MultipartFile file, T entity) {
        Path newFilePath = getNewFilePath(file, tags);
        try {
            Files.copy(file.getInputStream(), newFilePath);
        } catch (Exception e) {
            log.error("Can't save file: %s".formatted(e.getMessage()));
            throw new RuntimeException("Can't save file: %s".formatted(e.getMessage()));
        }
        String fileName = newFilePath.getFileName().toString();
        addNew(language, author, tags, fileName, entity);
    }

    public boolean sendPictureToEmail(String email, String pictureAddress, String userName) {
        File file = getFileFromFullAddress(pictureAddress);
        if (file != null) {
            return mailService.sendPictureToEmail(email, file, userName);
        } else {
            log.error("Can't find file %s".formatted(pictureAddress));
            return false;
        }
    }

    public boolean sendVideoToEmail(String email, String videoAddress, String userName) {
        File file = getFileFromFullAddress(videoAddress);
        if (file != null) {
            return mailService.sendVideoToEmail(email, file, userName);
        } else {
            log.error("Can't find file %s".formatted(videoAddress));
            return false;
        }
    }
}
