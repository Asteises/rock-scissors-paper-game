package ru.asteises.rockscissorspapergame.utils;

import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;

public class ButtonOption {

    @Getter
    private static final EnumMap<Options, String> buttonOptions = new EnumMap<>(Map.of(
            Options.TEXT_FIELD, "",
            Options.URL_FIELD, "",
            Options.CALLBACK_DATA_FIELD, ""
    ));

    public enum Options {
        TEXT_FIELD,
        URL_FIELD,
        CALLBACK_DATA_FIELD,
        CALLBACK_GAME_FIELD,
        SWITCH_INLINE_QUERY_FIELD,
        SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD,
        PAY_FIELD,
        LOGIN_URL_FIELD,
        WEBAPP_FIELD,

        ROCK,
        SCISSORS,
        PAPER;
    }
}
