package ru.asteises.rockscissorspapergame.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainBoard implements ReplyKeyboard {

    public ReplyKeyboard getMainKeyBoard() {
        InlineKeyboardMarkup mainKeyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton findAnOpponentButton = new InlineKeyboardButton();
        findAnOpponentButton.setText("Найти противника");
        findAnOpponentButton.setCallbackData("FIND_OPPONENT");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(findAnOpponentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        mainKeyboard.setKeyboard(rowList);

        return mainKeyboard;
    }

}
