package com.memnik.controller;

import com.memnik.common.constants.ElementTypes;
import com.memnik.controller.base.TextController;
import com.memnik.dao.QuoteEntity;
import com.memnik.dto.BaseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.memnik.common.constants.Constants.QUOTE_URL;

@Controller
@RequestMapping(QUOTE_URL)
public class QuoteController extends TextController<QuoteEntity> {
    @Override
    public QuoteEntity getEntity() {
        return entityCreator.createQuoteEntity();
    }
    @Override
    public String getEntityName() {
        return "quote";
    }
    @Override
    public List<String> getFilteredTagNames() {
        return tagService.getQuoteTagNames();
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

