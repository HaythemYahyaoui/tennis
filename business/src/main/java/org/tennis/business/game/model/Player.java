package org.tennis.business.game.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Player {

    private final String reference;
    private final String name;
    private final String lastname;
    private Score score;

    public Player(String reference, String name, String lastname) {
        this.reference = reference;
        this.name = name;
        this.lastname = lastname;
        this.score = Score.LOVE;
    }

    public void updateScore(Score score) {
        this.score = score;
    }

}
