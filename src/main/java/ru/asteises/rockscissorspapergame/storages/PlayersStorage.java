package ru.asteises.rockscissorspapergame.storages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Repository
public class PlayersStorage {

    public static final Map<Long, User> storage = new HashMap<>();
}
