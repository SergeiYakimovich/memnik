package com.memnik.dao.base;

import com.memnik.dao.*;
import org.springframework.stereotype.Component;

@Component
public class EntityCreator {
    public MemEntity createMemEntity() {
        return new MemEntity();
    }

    public JokeEntity createJokeEntity() {
        return new JokeEntity();
    }

    public PostcardEntity createPostcardEntity() {
        return new PostcardEntity();
    }
    public QuoteEntity createQuoteEntity() {
        return new QuoteEntity();
    }
    public VideoEntity createVideoEntity() {
        return new VideoEntity();
    }
}
