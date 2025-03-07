package org.tennis.business.game.port.output;

import lombok.*;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;

public interface GameEvent {

    void gameCreated(GameCreatedEvent gameCreatedEvent) throws InfrastructureException;

    void playDone(PlayDoneEvent playDoneEvent) throws InfrastructureException;

    void gameEnded(GameEndedEvent gameEndedEvent) throws InfrastructureException;

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class GameCreatedEvent {
        private String id;
        private String playerOneReference;
        private Score playerOneScore;
        private String playerTowReference;
        private Score playerTowScore;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class PlayDoneEvent {
        private String id;
        private String playerOneReference;
        private Score playerOneScore;
        private String playerTowReference;
        private Score playerTowScore;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class GameEndedEvent {
        private String id;
        private String playerOneReference;
        private Score playerOneScore;
        private String playerTowReference;
        private Score playerTowScore;
    }

}
