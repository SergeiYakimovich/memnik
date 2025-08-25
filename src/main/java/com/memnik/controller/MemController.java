package com.memnik.controller;

import com.memnik.common.constants.ElementTypes;
import com.memnik.controller.base.PictureController;
import com.memnik.dao.MemEntity;
import com.memnik.dto.BaseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.MEM_URL;
import static com.memnik.common.constants.Constants.STORAGE;

@Controller
@RequestMapping(MEM_URL)
public class MemController extends PictureController<MemEntity> {
    @Override
    public MemEntity getEntity() {
        return entityCreator.createMemEntity();
    }

    @Override
    public String getEntityName() {
        return "mem";
    }
    @Override
    public List<String> getFilteredTagNames() {
        return tagService.getMemTagNames();
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
