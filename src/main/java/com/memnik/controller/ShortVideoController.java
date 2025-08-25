package com.memnik.controller;

import com.memnik.common.constants.ElementTypes;
import com.memnik.controller.base.VideoController;
import com.memnik.dao.VideoEntity;
import com.memnik.dto.BaseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.STORAGE;
import static com.memnik.common.constants.Constants.VIDEO_URL;

@Controller
@RequestMapping(VIDEO_URL)
public class ShortVideoController extends VideoController<VideoEntity> {
    @Override
    public VideoEntity getEntity() {
        return entityCreator.createVideoEntity();
    }
    @Override
    public String getEntityName() {
        return "video";
    }
    @Override
    public List<String> getFilteredTagNames() {
        return tagService.getVideoTagNames();
    }
    @Override
    public String getTypeName() {
        return ElementTypes.VIDEO.name;
    }
    @Override
    public String getInformation(BaseDto baseDto) {
        return "/%s/%s".formatted(STORAGE, baseDto.getInformation());
    }
}
