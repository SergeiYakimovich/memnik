package com.memnik.service;

import com.memnik.dao.TagEntity;
import com.memnik.dao.VideoEntity;
import com.memnik.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getFileFromAddress;

@Slf4j
@Service
public class VideoService extends BaseService<VideoEntity> {
    public boolean deleteVideo(Long videoId) {
        Optional<VideoEntity> optionalVideo = repository.findById(videoId);
        if(optionalVideo.isEmpty()) {
            log.error("Can't find mem with id %d".formatted(videoId));
            return false;
        }
        VideoEntity video = optionalVideo.get();

        for (TagEntity tag : new ArrayList<>(video.getTags())) {
            tag.removeVideo(video);
        }

        File file = getFileFromAddress(video.getInformation());
        if(file != null) {
            file.delete();
        }
        repository.delete(video);
        log.info("Video %d has been deleted".formatted(videoId));

        return true;
    }
}
