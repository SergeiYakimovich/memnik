package com.memnik.common;

import com.memnik.common.constants.LanguageDictionary;
import com.memnik.common.constants.Languages;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LanguageResolver {
    private String currentLanguage = Languages.EN.name();

    public String resolve(LanguageDictionary languageDictionary) {
        return languageDictionary.get(currentLanguage);
    }
}
