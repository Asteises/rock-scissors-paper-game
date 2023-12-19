package ru.asteises.rockscissorspapergame.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.rockscissorspapergame.utils.ButtonOption;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public interface StandardKeyboard {

    default InlineKeyboardMarkup createKeyboard() {
        return new InlineKeyboardMarkup();
    }

    default InlineKeyboardButton createButton(EnumMap<ButtonOption.Options, String> options) {
        return InlineKeyboardButton.builder()
                .text(options.get(ButtonOption.Options.TEXT_FIELD))
                .callbackData(options.get(ButtonOption.Options.CALLBACK_DATA_FIELD))
                .url(options.get(ButtonOption.Options.URL_FIELD))
                .build();
    }

    default List<InlineKeyboardButton> createRow() {
        return new ArrayList<>();
    }

    default List<List<InlineKeyboardButton>> createRowList() {
        return new ArrayList<>();
    }
}
