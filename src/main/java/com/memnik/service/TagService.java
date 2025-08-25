package com.memnik.service;

import com.memnik.dao.*;
import com.memnik.dto.TagDto;
import com.memnik.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getFileFromAddress;

@Slf4j
@Service
@Transactional
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private MemRepository memRepository;
    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private PostcardRepository postcardRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private VideoRepository videoRepository;

    public boolean createTag(String name, String author) {
        Optional<TagEntity> tag = tagRepository.findByNameIgnoreCase(name);
        if(tag.isPresent()) {
            return false;
        }
        TagEntity tagEntity = new TagEntity();
        tagEntity.setAuthor(author);
        tagEntity.setName(name);
        tagEntity.setCreatedAt(LocalDateTime.now());
        tagRepository.save(tagEntity);
        log.info("Tag %s was created".formatted(name));
        return true;
    }

    public List<TagDto> getTags() {
        return  tagMapper.toDto(tagRepository.findAll()).stream()
                .sorted(Comparator.comparing(TagDto::getName))
                .toList();
    }

    public List<String> getTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public List<String> getMemTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .filter(s -> s.getMemsCount() > 0)
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public List<String> getJokeTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .filter(s -> s.getJokesCount() > 0)
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public List<String> getPostcardTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .filter(s -> s.getPostcardsCount() > 0)
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public List<String> getQuoteTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .filter(s -> s.getQuotesCount() > 0)
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public List<String> getVideoTagNames() {
        return tagMapper.toDto(tagRepository.findAll()).stream()
                .filter(s -> s.getVideosCount() > 0)
                .map(TagDto::getName)
                .sorted()
                .toList();
    }

    public boolean deleteTag(Long tagId) {
        Optional<TagEntity> optionalTag = tagRepository.findById(tagId);
        if(optionalTag.isEmpty()) {
            log.error("Can't find tag with id %d".formatted(tagId));
            return false;
        }
        TagEntity tag = optionalTag.get();

        for (MemEntity mem : new ArrayList<>(tag.getMems())) {
            mem.removeTag(tag);
            if (mem.getTags().isEmpty()) {
                File file = getFileFromAddress(mem.getInformation());
                if(file != null) {
                    file.delete();
                }
                memRepository.delete(mem);
            }
        }

        for (JokeEntity joke : new ArrayList<>(tag.getJokes())) {
            joke.removeTag(tag);
            if (joke.getTags().isEmpty()) {
                jokeRepository.delete(joke);
            }
        }

        for (PostcardEntity postcard : new ArrayList<>(tag.getPostcards())) {
            postcard.removeTag(tag);
            if (postcard.getTags().isEmpty()) {
                File file = getFileFromAddress(postcard.getInformation());
                if(file != null) {
                    file.delete();
                }
                postcardRepository.delete(postcard);
            }
        }

        for (QuoteEntity quote : new ArrayList<>(tag.getQuotes())) {
            quote.removeTag(tag);
            if (quote.getTags().isEmpty()) {
                quoteRepository.delete(quote);
            }
        }

        for (VideoEntity video : new ArrayList<>(tag.getVideos())) {
            video.removeTag(tag);
            if (video.getTags().isEmpty()) {
                File file = getFileFromAddress(video.getInformation());
                if(file != null) {
                    file.delete();
                }
                videoRepository.delete(video);
            }
        }
        tagRepository.delete(tag);
        log.info("Tag %d has been deleted".formatted(tagId));

        return true;
    }
}
