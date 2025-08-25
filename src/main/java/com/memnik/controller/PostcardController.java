package com.memnik.controller;

import com.memnik.common.constants.ElementTypes;
import com.memnik.controller.base.PictureController;
import com.memnik.dao.PostcardEntity;
import com.memnik.dto.BaseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.POSTCARD_URL;
import static com.memnik.common.constants.Constants.STORAGE;

@Controller
@RequestMapping(POSTCARD_URL)
public class PostcardController extends PictureController<PostcardEntity> {
    @Override
    public PostcardEntity getEntity() {
        return entityCreator.createPostcardEntity();
    }
    @Override
    public String getEntityName() {
        return "postcard";
    }
    @Override
    public List<String> getFilteredTagNames() {
        return tagService.getPostcardTagNames();
    }
    @Override
    public String getTypeName() {
        return ElementTypes.PICTURE.name;
    }
    @Override
    public String getInformation(BaseDto baseDto) {
        return "/%s/%s".formatted(STORAGE, baseDto.getInformation());
    }
}
