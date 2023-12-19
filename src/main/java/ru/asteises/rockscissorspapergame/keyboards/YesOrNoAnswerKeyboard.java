package ru.asteises.rockscissorspapergame.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.rockscissorspapergame.utils.ButtonOption;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

@Component
public class YesOrNoAnswerKeyboard implements ReplyKeyboard, StandardKeyboard {

    public ReplyKeyboard getYesOrNoAnswerKeyboard(Long userChatId) {

        InlineKeyboardMarkup yesOrNoAnswerKeyboard = createKeyboard();

        EnumMap<ButtonOption.Options, String> options = ButtonOption.getButtonOptions();
        UUID id = UUID.randomUUID();
        options.put(ButtonOption.Options.TEXT_FIELD, "ДА");
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, "YES$" + userChatId + "&" + id);
        InlineKeyboardButton yesButton = createButton(options);

        options.put(ButtonOption.Options.TEXT_FIELD, "НЕТ");
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, "NO$" + userChatId);
        InlineKeyboardButton noButton = createButton(options);

        List<InlineKeyboardButton> row = createRow();
        row.add(yesButton);
        row.add(noButton);

        List<List<InlineKeyboardButton>> rowList = createRowList();
        rowList.add(row);
        yesOrNoAnswerKeyboard.setKeyboard(rowList);

        return yesOrNoAnswerKeyboard;
    }

}
