package com.memnik.tgbot;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class TgBotConstants {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData(START_COMMAND);
        HELP_BUTTON.setCallbackData(HELP_COMMAND);

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, HELP_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String TAG_COMMAND = "/tags";
    public static final String MEM_COMMAND = "/mem";
    public static final String JOKE_COMMAND = "/joke";
    public static final String POSTCARD_COMMAND = "/postcard";
    public static final String QUOTE_COMMAND = "/quote";
    public static final String VIDEO_COMMAND = "/video";
    public static final List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand(START_COMMAND, "start the bot"),
            new BotCommand(HELP_COMMAND, "bot info")
    );
    public static final String START_TEXT = "Hi, %s. Nice to meet you! Enjoy Memnik!";
    public static final String HELP_TEXT = """
           The following commands are available to you:
           %s   - start the bot
           %s   - help menu
           
           %s           - existing tags
           %s tag1 tag2 - find mem with tags
           %s tag1 tag2 - find joke with tags
           %s tag1 tag2 - find postcard with tags
           %s tag1 tag2 - find quote with tags
           %s tag1 tag2 - find video with tags
            """.formatted(START_COMMAND, HELP_COMMAND, TAG_COMMAND, MEM_COMMAND, JOKE_COMMAND,
            POSTCARD_COMMAND, QUOTE_COMMAND, VIDEO_COMMAND);
}
