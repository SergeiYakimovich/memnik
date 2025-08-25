package com.memnik.common.constants;

import com.memnik.dao.user.Role;

import java.util.Map;

public class Constants {
    public static final String STORAGE = "storage";
    public static final String ANY_AUTHOR = "ANY";
    public static final String MY_AUTHOR = "MY";

    // Security Constants
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String USER_ROLE_TEXT = "ROLE_USER";
    public static final String ADMIN_ROLE_TEXT = "ROLE_ADMIN";
    public static final Role USER_ROLE = new Role(1L, USER_ROLE_TEXT);
    public static final Role ADMIN_ROLE = new Role(2L, ADMIN_ROLE_TEXT);
    public static final Map<String, Role> ROLE_MAP = Map.of(
            USER_ROLE_TEXT, USER_ROLE, ADMIN_ROLE_TEXT, ADMIN_ROLE);
    public static final int MIN_PASSWORD_LENGTH = 3;
    public static final int MAX_PASSWORD_LENGTH = 20;

    // URL Constants
    public static final String REGISTRATION_URL = "registration";
    public static final String LOGIN_URL = "login";
    public static final String MEM_URL = "mem";
    public static final String JOKE_URL = "joke";
    public static final String TAG_URL = "tag";
    public static final String ADD_URL = "add";
    public static final String FIND_URL = "find";
    public static final String SEND_URL = "send";
    public static final String ADMIN_URL = "admin";
    public static final String HOME_URL = "/";
    public static final String CONFIRM_URL = "confirm";
    public static final String UNSUBSCRIBE_URL = "unsubscribe";
    public static final String CONTACTS_URL = "contacts";
    public static final String QUOTE_URL = "quote";
    public static final String POSTCARD_URL = "postcard";
    public static final String VIDEO_URL = "video";

    // ShedLock Constants
    public static final String DELETE_UNCONFIRMED_FIXED_DELAY = "PT1H";
    public static final String DELETE_UNCONFIRMED_INIT_DELAY = "PT1H";
    public static final String NOTIFY_USERS_FIXED_DELAY = "PT24H";
    public static final String NOTIFY_USERS_INIT_DELAY = "PT24H";
    public static final String LOCK_AT_LEAST_FOR = "40s";
    public static final String LOCK_AT_MOST_FOR = "50s";
}
