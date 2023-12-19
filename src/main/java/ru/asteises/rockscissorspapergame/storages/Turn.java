package ru.asteises.rockscissorspapergame.storages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.asteises.rockscissorspapergame.utils.GameAnswer;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Turn {

    private Map<Long, GameAnswer> player1Turn;
    private Map<Long, GameAnswer> player2Turn;

}
