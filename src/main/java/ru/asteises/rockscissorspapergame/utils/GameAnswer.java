package ru.asteises.rockscissorspapergame.utils;

public enum GameAnswer {

    ROCK("КАМЕНЬ"),
    SCISSORS("НОЖНИЦЫ"),
    PAPER("БУМАГА"),

    YES("YES"),
    NO("NO");

    public final String name;

    GameAnswer(String name) {
        this.name = name;
    }

    public static GameAnswer valueOfName(String name) {
        for (GameAnswer answer : values()) {
            if (answer.name.equals(name)) {
                return answer;
            }
        }
        return null;
    }
}
