package com.memnik.common.constants;

public enum LanguageDictionary {
    HELLO_TITLE("Welcome to Memnik!", "Добро пожаловать в Мемник!"),
    HELLO_1_ANONIMUS("Hello", "Привет"),
    HELLO_1_USER("Hello, %s", "Привет %s"),
    HELLO_2_ANONIMUS("Please, register or login", "Пожалуйста, зарегистрируйтесь или войдите в приложение"),
    HELLO_2_USER("Now you can enjoy Memnik application", "Теперь вы можете наслаждаться приложением Мемник"),
    HELLO_3("Memnik is application for saving and sharing mems, jokes, postcards, quotes and short videos",
            "Мемник - это приложение для хранения мемов, анекдотов, открыток, цитат, коротких видео и обмена ими"),
    HELLO_4("You can find here mems, jokes, postcards, quotes, short videos and add new ones",
            "Вы можете найти мемы, шутки, открытки, цитаты, короткие видео и добавить новые"),
    HELLO_5("Also you can visit our Telegram   t.me/%s   and get memes, jokes, postcards, quotes and short videos from it",
            "Также вы можете посетить наш Телеграм   t.me/%s   и получить мемы, шутки, открытки, цитаты и короткие видео из него"),
    REGISTRATION_1("User %s has been created", "Пользователь %s создан"),
    REGISTRATION_2("Now you can login and start using application", "Теперь вы можете войти и начать использовать приложение"),
    REGISTRATION_3_WAS_SENT("Memnik sent letter to your email %s", "Memnik отправил письмо на вашу почту %s"),
    REGISTRATION_4_WAS_SENT("Please confirm your email or user %s will be deleted soon", "Пожалуйста, подтвердите свой email или пользователь %s будет удален в ближайшее время"),
    REGISTRATION_3_CANT_SENT("Can't send confirmation letter to %s", "Не удалось отправить письмо на почту %s"),
    REGISTRATION_4_CANT_SENT("Please, register again, because user %s will be deleted soon", "Пожалуйста, зарегистрируйтесь снова, потому что пользователь %s будет удален в ближайшее время"),
    CONFIRM_EMAIL_1_OK("Your email has been confirmed", "Ваш email был подтвержден"),
    CONFIRM_EMAIL_2_OK("Enjoy our application", "Наслаждайтесь нашим приложением"),
    CONFIRM_EMAIL_1_FAIL("Can't find user and confirm email", "Не удалось подтвердить почту"),
    CONFIRM_EMAIL_2_FAIL("Please, register again", "Пожалуйста, зарегистрируйтесь снова"),
    UNSUBSCRIBE_1_OK("Unsubscribed successfully", "Успешно отписан"),
    UNSUBSCRIBE_2_OK("No more emails from memnik", "Больше не будет писем от memnik"),
    UNSUBSCRIBE_1_FAIL("Can't find user and unsubscribe", "Не удалось найти пользователя и отписаться"),

    ;

    final String[] values;
    LanguageDictionary(String... values) {
        this.values = values;
    }

    public String get(String lang) {
        if(lang.equals(Languages.EN.name())) {
            return values[0];
        } else {
            return values[1];
        }
    }
}
