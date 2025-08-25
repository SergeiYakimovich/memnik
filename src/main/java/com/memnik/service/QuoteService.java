package com.memnik.service;

import com.memnik.dao.QuoteEntity;
import com.memnik.dao.TagEntity;
import com.memnik.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class QuoteService extends BaseService<QuoteEntity> {
    public boolean deleteQuote(Long quoteId) {
        Optional<QuoteEntity> optionalQuote = repository.findById(quoteId);
        if(optionalQuote.isEmpty()) {
            log.error("Can't find mem with id %d".formatted(quoteId));
            return false;
        }
        QuoteEntity quote = optionalQuote.get();

        for (TagEntity tag : new ArrayList<>(quote.getTags())) {
            tag.removeQuote(quote);
        }

        repository.delete(quote);
        log.info("Mem %d has been deleted".formatted(quoteId));

        return true;
    }
}
