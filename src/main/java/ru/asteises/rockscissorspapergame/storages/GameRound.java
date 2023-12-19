package ru.asteises.rockscissorspapergame.storages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GameRound {

    private UUID id;

    private Long player1ChatId;
    private Long player2ChatId;

    private int player1wins;
    private int player2wins;

    private int turn;

    private Map<Integer, Turn> turns;
}
