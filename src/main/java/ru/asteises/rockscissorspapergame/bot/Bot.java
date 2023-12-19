package ru.asteises.rockscissorspapergame.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.rockscissorspapergame.callback.CallbacksHandler;
import ru.asteises.rockscissorspapergame.commands.CommandHandler;
import ru.asteises.rockscissorspapergame.commands.StartCommand;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class Bot extends TelegramLongPollingCommandBot {

    private final CommandHandler commandHandler;
    private final CallbacksHandler callbacksHandler;

    public Bot(String botToken, CommandHandler commandHandler, CallbacksHandler callbacksHandler) {
        super(botToken);
        this.commandHandler = commandHandler;
        this.callbacksHandler = callbacksHandler;

        register(new StartCommand("/start", "Start command"));
    }

    @Override
    public String getBotUsername() {
        return "ROCK-SCISSORS-PAPER-GAME";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        if (updates.size() > 1) {
            log.info("updates > 1: {}", updates);
            for (Update update : updates) {
                System.out.println(update.toString());
            }
        }
        Update update = updates.get(0);
        SendMessage result = new SendMessage();
        // если пришел текст;
        if (update.hasMessage() && update.getMessage().hasText() && !update.hasCallbackQuery()) {
            String messageText = update.getMessage().getText();
            // если текст начинается с /, то это команда;
            if (messageText.startsWith("/")) {
                result = commandHandler.handleCommands(result, update);
            } else {
                log.info("command: {}", messageText);
            }
            // или это callback;
        } else if (update.hasCallbackQuery()) {
            Map<Long, SendMessage> messages = callbacksHandler.handleCallbacks(result, update);
            if (messages.size() > 1) {
                for (SendMessage sendMessage : messages.values()) {
                    if (result.getChatId().equals(sendMessage.getChatId())) {
                        // отправляем сообщение пользователю;
                        result = messages.get(update.getCallbackQuery().getMessage().getChatId());
                    } else {
                        // отправляем сообщение оппоненту;
                        sendMessage(sendMessage);
                    }
                }
            }
        }
        sendMessage(result);
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
    }
}
