package com.memnik.common;

import com.memnik.common.constants.Languages;
import com.memnik.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static com.memnik.common.constants.Constants.ANONYMOUS_USER;

@Getter
@Setter
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUserResolver {
    private UserDto currentUser = UserDto.builder()
            .name(ANONYMOUS_USER)
            .language(Languages.ANY.name())
            .build();

}
