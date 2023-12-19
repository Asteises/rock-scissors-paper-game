package ru.asteises.rockscissorspapergame.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterKeyboard implements ReplyKeyboard {

    public ReplyKeyboard getRegisterKeyboard() {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton infoButton = new InlineKeyboardButton();
        InlineKeyboardButton registerButton = new InlineKeyboardButton();

        infoButton.setText("Прочитать информацию об игре");
        infoButton.setCallbackData("INFO");

        registerButton.setText("Стать участником");
        registerButton.setCallbackData("REG");

        keyboardButtonsRow.add(infoButton);
        keyboardButtonsRow.add(registerButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        result.setKeyboard(rowList);

        return result;
    }
}
