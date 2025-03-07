package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;


public interface FindGameUseCase {

    GameView findGame(FindGameAction playAction) throws InfrastructureException;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class FindGameAction {

        @NotEmpty
        private String id;

    }

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class GameView {

        @NotEmpty
        private final String id;

        @Valid
        @NotNull
        private final PlayerDto playerOne;

        @Valid
        @NotNull
        private final PlayerDto playerTow;

    }

    @Getter
    @Builder
    @ToString
    class PlayerDto {

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
