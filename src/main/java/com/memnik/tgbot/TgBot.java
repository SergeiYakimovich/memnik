package com.memnik.tgbot;

import com.memnik.common.CommonUtils;
import com.memnik.common.constants.Languages;
import com.memnik.dto.BaseDto;
import com.memnik.service.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getTagsFromMessage;
import static com.memnik.common.CommonUtils.tagNamesToString;
import static com.memnik.common.constants.Constants.ANY_AUTHOR;
import static com.memnik.tgbot.TgBotConstants.*;

@Slf4j
@Component
public class TgBot extends TelegramLongPollingBot {
    @Value("${memnik.tgbot.name}")
    private String tgbotName;
    @Value("${memnik.tgbot.token}")
    private String tgbotToken;
    @Autowired
    private MemService memService;
    @Autowired
    private JokeService jokeService;
    @Autowired
    private PostcardService postcardService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private TagService tagService;

    @Override
    public String getBotUsername() {
        return tgbotName;
    }
    @Override
    public String getBotToken() {
        return tgbotToken;
    }

    @PostConstruct
    public void init() {
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.info("Error creating bot " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getFirstName();
            String messageText = update.getMessage().getText();

            handleMessageText(chatId, userName, messageText);
            return;
        }

        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String userName = update.getCallbackQuery().getFrom().getFirstName();
            String messageText = update.getCallbackQuery().getData();

            handleMessageText(chatId, userName, messageText);
        }
    }

    private void handleMessageText(long chatId, String userName, String messageText) {
        if (messageText.startsWith(START_COMMAND)) {
            sendTextToTelegram(chatId, START_TEXT.formatted(userName));
            return;
        }
        if (messageText.startsWith(HELP_COMMAND)) {
            sendTextToTelegram(chatId, TgBotConstants.HELP_TEXT);
            return;
        }

        if (messageText.startsWith(TAG_COMMAND)) {
            sendTagsToTelegram(chatId);
            return;
        }

        if (messageText.startsWith(MEM_COMMAND)) {
            sendMemToTelegram(chatId, messageText);
            return;
        }
        if (messageText.startsWith(JOKE_COMMAND)) {
            sendJokeToTelegram(chatId, messageText);
            return;
        }
        if (messageText.startsWith(POSTCARD_COMMAND)) {
            sendPostcardToTelegram(chatId, messageText);
            return;
        }
        if (messageText.startsWith(QUOTE_COMMAND)) {
            sendQuoteToTelegram(chatId, messageText);
            return;
        }
        if (messageText.startsWith(VIDEO_COMMAND)) {
            sendVideoToTelegram(chatId, messageText);
            return;
        }

        sendTextToTelegram(chatId, "Unknown command, try /help");
    }

    private void sendTagsToTelegram(long chatId) {
        log.info("Sending tags to Telegram");
        List<String> tags = tagService.getTagNames();
        String textToSend = String.join("\n ", tags);
        sendTextToTelegram(chatId, textToSend);
    }

    private void sendMemToTelegram(long chatId, String messageText) {
        log.info("Sending mem to Telegram");
        List<String> tags = getTagsFromMessage(messageText);
        Optional<File> file = memService.findNew(Languages.ANY.name(), tags, ANY_AUTHOR)
                .map(BaseDto::getInformation)
                .map(CommonUtils::getFileFromAddress);
        if (file.isPresent()) {
            InputFile photo = new InputFile(file.get());
            sendPictureToTelegram(chatId, photo);
        } else {
            sendTextToTelegram(chatId, "No mem with tags %s found".formatted(tagNamesToString(tags)));
        }
    }

    private void sendPostcardToTelegram(long chatId, String messageText) {
        log.info("Sending postcard to Telegram");
        List<String> tags = getTagsFromMessage(messageText);
        Optional<File> file = postcardService.findNew(Languages.ANY.name(), tags, ANY_AUTHOR)
                .map(BaseDto::getInformation)
                .map(CommonUtils::getFileFromAddress);
        if (file.isPresent()) {
            InputFile photo = new InputFile(file.get());
            sendPictureToTelegram(chatId, photo);
        } else {
            sendTextToTelegram(chatId, "No postcard with tags %s found".formatted(tagNamesToString(tags)));
        }
    }

    private void sendJokeToTelegram(long chatId, String messageText) {
        log.info("Sending joke to Telegram");
        List<String> tags = getTagsFromMessage(messageText);
        Optional<String> textToSend = jokeService.findNew(Languages.ANY.name(), tags, ANY_AUTHOR)
                .map(BaseDto::getInformation);
        if(textToSend.isPresent()) {
            sendTextToTelegram(chatId, textToSend.get());
        } else {
            sendTextToTelegram(chatId, "No jokes with this tags %s found".formatted(tagNamesToString(tags)));
        }
    }

    private void sendQuoteToTelegram(long chatId, String messageText) {
        log.info("Sending quote to Telegram");
        List<String> tags = getTagsFromMessage(messageText);
        Optional<String> textToSend = quoteService.findNew(Languages.ANY.name(), tags, ANY_AUTHOR)
                .map(BaseDto::getInformation);
        if(textToSend.isPresent()) {
            sendTextToTelegram(chatId, textToSend.get());
        } else {
            sendTextToTelegram(chatId, "No quotes with this tags %s found".formatted(tagNamesToString(tags)));
        }
    }

    private void sendVideoToTelegram(long chatId, String messageText) {
        log.info("Sending short video to Telegram");
        List<String> tags = getTagsFromMessage(messageText);
        Optional<File> file = videoService.findNew(Languages.ANY.name(), tags, ANY_AUTHOR)
                .map(BaseDto::getInformation)
                .map(CommonUtils::getFileFromAddress);
        if (file.isPresent()) {
            InputFile video = new InputFile(file.get());
            sendAnimationToTelegram(chatId, video);
        } else {
            sendTextToTelegram(chatId, "No video with tags %s found".formatted(tagNamesToString(tags)));
        }
    }

    private void sendAnimationToTelegram(long chatId, InputFile video) {
        log.info("Sending animation to Telegram");
        try {
            SendAnimation sendAnimation = SendAnimation.builder()
                    .chatId(chatId)
                    .animation(video)
                    .build();
            execute(sendAnimation);
        } catch (Exception e) {
            log.error("Error while sending animation to Telegram " + e.getMessage());
        }
    }

    private void sendPictureToTelegram(long chatId, InputFile picture) {
        log.info("Sending picture to Telegram");
        try {
            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(picture)
                    .build();
            execute(sendPhoto);
        } catch (Exception e) {
            log.error("Error while sending picture to Telegram " + e.getMessage());
        }
    }

    private void sendTextToTelegram(Long chatId, String textToSend) {
        log.info("Sending text to Telegram: "+ textToSend);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(TgBotConstants.inlineMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending text to Telegram "+ e.getMessage());
        }
    }

}
