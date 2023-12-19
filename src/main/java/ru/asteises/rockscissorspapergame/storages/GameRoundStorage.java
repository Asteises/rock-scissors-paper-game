package ru.asteises.rockscissorspapergame.storages;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameRoundStorage {

    @Getter
    public static final Map<UUID, GameRound> games = new HashMap<>();

}
