package org.tennis.business.game.port.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;


public interface PlayGameUseCase {

    PlayResponse play(PlayAction playAction) throws InfrastructureException, DomainException;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class PlayAction {

        @NotEmpty
        private String gameId;

        @NotEmpty
        private String playerReference;

    }

    @Getter
    @Builder
    @ToString
    class PlayResponse {

        @NotNull
        private PlayerView playerView;

    }

    @Getter
    @Builder
    @ToString
    class PlayerView {

        @NotEmpty
        private String reference;

        @NotEmpty
        private String name;

        @NotEmpty
        private String lastname;

        @NotNull
        private Score score;
    }


}
