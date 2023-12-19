package ru.asteises.rockscissorspapergame.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rockscissorspapergame.keyboards.GameKeyboard;
import ru.asteises.rockscissorspapergame.keyboards.InfoKeyboard;
import ru.asteises.rockscissorspapergame.keyboards.MainBoard;
import ru.asteises.rockscissorspapergame.keyboards.YesOrNoAnswerKeyboard;
import ru.asteises.rockscissorspapergame.storages.GameRound;
import ru.asteises.rockscissorspapergame.storages.GameRoundStorage;
import ru.asteises.rockscissorspapergame.storages.PlayersStorage;
import ru.asteises.rockscissorspapergame.storages.Turn;
import ru.asteises.rockscissorspapergame.utils.GameAnswer;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallbacksHandler {

    private final InfoKeyboard infoKeyboard;
    private final MainBoard mainBoard;
    private final YesOrNoAnswerKeyboard yesOrNoAnswerKeyboard;
    private final GameKeyboard gameKeyboard;

    public Map<Long, SendMessage> handleCallbacks(SendMessage message, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        log.info("callback data: {}", callbackQuery.getData());

        Map<Long, SendMessage> result = new HashMap<>();

//        List<String> list = JsonHandler.toList(callbackQuery.getData());
//        log.info("list size: {}", list.size());

        Long userChatId = callbackQuery.getMessage().getChatId();
        message.setChatId(userChatId);

        String data = callbackQuery.getData();
        if (data.contains("$")) {

            String answer = data.substring(0, data.indexOf("$"));
            log.info("answer: {}", answer);
            String opponentChatIdString = data.substring(data.indexOf("$") + 1, data.indexOf("&"));
            Long opponentChatIdLong = Long.parseLong(opponentChatIdString);
            log.info("opponentChatId: {}", opponentChatIdLong);
            UUID key = UUID.fromString(data.substring(data.indexOf("&") + 1));
            log.info("key: {}", key);
            GameAnswer gameAnswer = GameAnswer.valueOfName(answer);
            log.info("value of answer: {}", gameAnswer);

            switch (Objects.requireNonNull(gameAnswer)) {
                case YES -> {
                    GameRound round = GameRoundStorage.games.getOrDefault(
                            key,
                            new GameRound(
                                    key,
                                    userChatId,
                                    opponentChatIdLong,
                                    0,
                                    0,
                                    1
                                    , new HashMap<>())
                    );
                    GameRoundStorage.games.put(key, round);
                    log.info("round YES: {}", round);

                    SendMessage opponentMessage = new SendMessage();
                    opponentMessage.setText("Противник найден, начинаем!");
                    opponentMessage.setChatId(opponentChatIdLong);
                    opponentMessage.setReplyMarkup(gameKeyboard.getGameKeyboard(round));
                    result.put(opponentChatIdLong, opponentMessage);

                    message.setText("Выш выбор:");
                    message.setReplyMarkup(gameKeyboard.getGameKeyboard(round));
                    result.put(userChatId, message);

                    return result;
                }
                case NO -> {
                    searchOpponent(message, userChatId);
                    result.put(userChatId, message);
                    return result;
                }
                case ROCK -> {
                    GameRound round = GameRoundStorage.games.get(key);
                    log.info("round ROCK: {}", round);
                    Turn turn = round.getTurns().getOrDefault(round.getTurn(), new Turn(
                            new HashMap<>(),
                            new HashMap<>()
                    ));

                    if (round.getPlayer1ChatId().equals(userChatId)) {
                        turn.getPlayer1Turn().put(userChatId, gameAnswer);
                    } else if (round.getPlayer2ChatId().equals(userChatId)) {
                        turn.getPlayer2Turn().put(userChatId, gameAnswer);
                    } else {
                        throw new RuntimeException("WRONG USER");
                    }
                    round.getTurns().put(round.getTurn(), turn);
                    log.info("round ROCK LAST: {}", round);

                    result = checkWin(round);
                    log.info("result: {}", result);

                    return result;
                }
                case SCISSORS -> {

                }
            }
        }
        switch (data) {
            case "INFO" -> {
                message.setText("BLA BLA BLA");
                message.setReplyMarkup(infoKeyboard.getInfoKeyboard());
                result.put(userChatId, message);
                return result;
            }
            case "REG" -> {

                PlayersStorage.storage.put(callbackQuery.getMessage().getChatId(), callbackQuery.getFrom());
                log.info("storage size: {}", PlayersStorage.storage.size());
                message.setText("Благодарим за регистрацию");
                message.setReplyMarkup(mainBoard.getMainKeyBoard());
                result.put(userChatId, message);
                return result;
            }
            case "FIND_OPPONENT" -> {
                if (PlayersStorage.storage.size() == 1) {
                    message.setText("К сожалению, других игроков сейчас нет");
                    result.put(userChatId, message);
                    return result;
                } else {
                    searchOpponent(message, userChatId);
                }
                result.put(userChatId, message);
                return result;
            }
        }
        result.put(userChatId, message);
        return result;
    }

    private void searchOpponent(SendMessage message, long userChatId) {
        List<Long> opponentsChatIds = PlayersStorage.storage.keySet().stream().collect(Collectors.toList());
        opponentsChatIds.remove(userChatId);
        log.info("opponents list size: {}", opponentsChatIds.size());
        int randomIndex = new Random().nextInt(opponentsChatIds.size());
        Long randomChatId = opponentsChatIds.get(randomIndex);
        message.setChatId(randomChatId);
        message.setText("Желаете сыграть?");
        message.setReplyMarkup(yesOrNoAnswerKeyboard.getYesOrNoAnswerKeyboard(userChatId));
    }

    private Map<Long, SendMessage> checkWin(GameRound round) {
        Map<Long, SendMessage> result = new HashMap<>();
        GameAnswer player1Answer = round.getTurns().get(round.getTurn()).getPlayer1Turn().get(round.getPlayer1ChatId());
        GameAnswer player2Answer = round.getTurns().get(round.getTurn()).getPlayer2Turn().get(round.getPlayer2ChatId());
        if (player1Answer != null && player2Answer != null) {
            if (player1Answer.equals(GameAnswer.ROCK) && player2Answer.equals(GameAnswer.ROCK)) {
                SendMessage p1Message  = new SendMessage();
                SendMessage p2Message  = new SendMessage();

                p1Message.setText("НИЧЬЯ");
                p1Message.setChatId(round.getPlayer1ChatId());

                p2Message.setText("НИЧЬЯ");
                p2Message.setChatId(round.getPlayer1ChatId());

                result.put(round.getPlayer1ChatId(), p1Message);
                result.put(round.getPlayer1ChatId(), p2Message);
            }
        }
        return result;
    }
}
