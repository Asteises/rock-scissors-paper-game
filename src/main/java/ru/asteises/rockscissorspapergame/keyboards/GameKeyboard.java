package ru.asteises.rockscissorspapergame.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.rockscissorspapergame.storages.GameRound;
import ru.asteises.rockscissorspapergame.utils.ButtonOption;
import ru.asteises.rockscissorspapergame.utils.GameAnswer;

import java.util.EnumMap;
import java.util.List;

@Component
public class GameKeyboard implements ReplyKeyboard, StandardKeyboard {

    public ReplyKeyboard getGameKeyboard(GameRound gameRound) {

        String firstDelimiter = "$";
        String secondDelimiter = "&";

        String delimiterAndUserIdAndRoundId = firstDelimiter + gameRound.getPlayer1ChatId() + secondDelimiter + gameRound.getId();

        InlineKeyboardMarkup gameKeyboard = createKeyboard();

        EnumMap<ButtonOption.Options, String> options = ButtonOption.getButtonOptions();
        options.put(ButtonOption.Options.TEXT_FIELD, GameAnswer.ROCK.name);
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, GameAnswer.ROCK.name + delimiterAndUserIdAndRoundId);
        InlineKeyboardButton rockButton = createButton(options);

        options.put(ButtonOption.Options.TEXT_FIELD, GameAnswer.SCISSORS.name);
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, GameAnswer.SCISSORS.name + delimiterAndUserIdAndRoundId);
        InlineKeyboardButton scissorsButton = createButton(options);

        options.put(ButtonOption.Options.TEXT_FIELD, GameAnswer.PAPER.name);
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, GameAnswer.PAPER.name + delimiterAndUserIdAndRoundId);
        InlineKeyboardButton paperButton = createButton(options);

        List<InlineKeyboardButton> row = createRow();
        row.add(rockButton);
        row.add(scissorsButton);
        row.add(paperButton);

        List<List<InlineKeyboardButton>> rowList = createRowList();
        rowList.add(row);
        gameKeyboard.setKeyboard(rowList);

        return gameKeyboard;
    }
}
