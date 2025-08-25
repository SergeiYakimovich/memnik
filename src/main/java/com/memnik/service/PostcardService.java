package com.memnik.service;

import com.memnik.dao.MemEntity;
import com.memnik.dao.PostcardEntity;
import com.memnik.dao.TagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getFileFromAddress;

@Slf4j
@Service
public class PostcardService extends com.memnik.service.base.BaseService<PostcardEntity> {
    public boolean deletePostcard(Long postcardId) {
        Optional<PostcardEntity> optionalPostcard = repository.findById(postcardId);
        if(optionalPostcard.isEmpty()) {
            log.error("Can't find mem with id %d".formatted(postcardId));
            return false;
        }
        PostcardEntity postcard = optionalPostcard.get();

        for (TagEntity tag : new ArrayList<>(postcard.getTags())) {
            tag.removePostcard(postcard);
        }

        File file = getFileFromAddress(postcard.getInformation());
        if(file != null) {
            file.delete();
        }
        repository.delete(postcard);
        log.info("Postcard %d has been deleted".formatted(postcardId));

        return true;
    }
}
