package ru.asteises.rockscissorspapergame.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.asteises.rockscissorspapergame.bot.Bot;
import ru.asteises.rockscissorspapergame.callback.CallbacksHandler;
import ru.asteises.rockscissorspapergame.commands.CommandHandler;

@Configuration
public class BotConfig {

    @Bean
    public Bot bot(@Value("${telegram.bot.token}") String botToken,
                   CommandHandler commandHandler,
                   CallbacksHandler callbacksHandler) {
        return new Bot(botToken, commandHandler, callbacksHandler);
    }

    @Bean
    public TelegramBotsApi botsApi(Bot bot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return telegramBotsApi;
    }
}
