package ru.asteises.rockscissorspapergame.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.rockscissorspapergame.utils.ButtonOption;

import java.util.*;

@Component
public class InfoKeyboard implements ReplyKeyboard, StandardKeyboard {

    public ReplyKeyboard getInfoKeyboard() {

        InlineKeyboardMarkup infoKeyboard = createKeyboard();

        EnumMap<ButtonOption.Options, String> options = ButtonOption.getButtonOptions();
        options.put(ButtonOption.Options.TEXT_FIELD, "Стать участником");
        options.put(ButtonOption.Options.CALLBACK_DATA_FIELD, "REG");
        InlineKeyboardButton registrationButton = createButton(options);

        List<InlineKeyboardButton> row = createRow();
        row.add(registrationButton);

        List<List<InlineKeyboardButton>> rowList = createRowList();
        rowList.add(row);
        infoKeyboard.setKeyboard(rowList);

        return infoKeyboard;
    }

}
