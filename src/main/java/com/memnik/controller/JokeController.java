package com.memnik.controller;

import com.memnik.common.constants.ElementTypes;
import com.memnik.controller.base.TextController;
import com.memnik.dao.JokeEntity;
import com.memnik.dto.BaseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.JOKE_URL;

@Controller
@RequestMapping(JOKE_URL)
public class JokeController extends TextController<JokeEntity> {
    @Override
    public JokeEntity getEntity() {
        return entityCreator.createJokeEntity();
    }
    @Override
    public String getEntityName() {
        return "joke";
    }
    @Override
    public List<String> getFilteredTagNames() {
        return tagService.getJokeTagNames();
    }
    @Override
    public String getTypeName() {
        return ElementTypes.TEXT.name;
    }
    @Override
    public String getInformation(BaseDto baseDto) {
        return baseDto.getInformation();
    }
}

