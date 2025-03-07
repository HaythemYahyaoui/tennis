package org.tennis.business.game.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.tennis.business.exception.DomainException;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Game {

    private final String id;
    private final Player playerOne;
    private final Player playerTow;

    public boolean isEnded() {
        return Score.GAME.equals(playerOne.getScore()) || Score.GAME.equals(playerTow.getScore());
    }

    public Player play(String playerReference) throws DomainException {
        if (isEnded()) {
            throw new DomainException("Game has already ended");
        }
        if (playerReference.equals(playerOne.getReference())) {
            calcule(this.playerOne, this.playerTow);
        } else if (playerReference.equals(playerTow.getReference())) {
            calcule(this.playerTow, this.playerOne);
        } else {
            throw new DomainException("Invalid player reference");
        }
        return playerReference.equals(playerOne.getReference()) ? playerOne : playerTow;
    }

    private void calcule(Player playerToAddPoint, Player otherPlayer) throws DomainException {
        if (Score.LOVE == playerToAddPoint.getScore() || Score.FIFTEEN == playerToAddPoint.getScore()) {
            playerToAddPoint.updateScore(Score.fromPoints(playerToAddPoint.getScore().getPoints() + 1));
        } else if (Score.THIRTY.equals(playerToAddPoint.getScore())) {
            if (Score.FORTY.equals(otherPlayer.getScore())) {
                playerToAddPoint.updateScore(Score.DEUCE);
                otherPlayer.updateScore(Score.DEUCE);
            } else if (Score.LOVE == otherPlayer.getScore() || Score.FIFTEEN == otherPlayer.getScore() || Score.THIRTY == otherPlayer.getScore()) {
                playerToAddPoint.updateScore(Score.FORTY);
            } else {
                throw new DomainException("Invalid score : can't happen !");
            }
        } else if (Score.FORTY.equals(playerToAddPoint.getScore())) {
            playerToAddPoint.updateScore(Score.GAME);
        } else if (Score.DEUCE.equals(playerToAddPoint.getScore())) {
            playerToAddPoint.updateScore(Score.ADVANTAGE);
        } else if (Score.ADVANTAGE.equals(playerToAddPoint.getScore())) {
            playerToAddPoint.updateScore(Score.GAME);
        } else {
            throw new DomainException("Invalid score : can't happen !");
        }
    }

}
