package com.memnik.factory;

import com.memnik.dao.PostcardEntity;
import com.memnik.dao.QuoteEntity;
import com.memnik.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class QuoteFactory {
    public static final Long QUOTE_ID = 1L;
    public static final String AUTHOR = "author";
    public static final String ADDRESS = "storage\\image0.jpeg";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final String TAG_NAMES = "tags";
    public static final BaseDto QUOTE_DTO = BaseDto.builder()
            .id(QUOTE_ID)
            .author(AUTHOR)
            .information(ADDRESS)
            .tags(List.of(TAG_NAMES))
            .createdAt(CREATED_AT)
            .used(false)
            .language("RU")
            .build();

    public static QuoteEntity getQuoteEntity() {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setAuthor(AUTHOR);
        quoteEntity.setInformation(ADDRESS);
        quoteEntity.setCreatedAt(CREATED_AT);
        quoteEntity.setUsed(false);
        quoteEntity.setLanguage("RU");
        return quoteEntity;
    }
    public static QuoteEntity getQuoteEntityWithId() {
        QuoteEntity quoteEntity = getQuoteEntity();
        quoteEntity.setId(QUOTE_ID);
        return quoteEntity;
    }
}
