package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.tennis.business.exception.InfrastructureException;


public interface CreateGameUseCase {

    CreateResponse create(CreateAction createAction) throws InfrastructureException;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class CreateAction {

        @Valid
        @NotNull
        private PlayerDto playerOne;

        @Valid
        @NotNull
        private PlayerDto playerTow;

    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class PlayerDto {

        @NotEmpty
        private String reference;

        @NotEmpty
        private String name;

        @NotEmpty
        private String lastname;

    }

    @Getter
    @Builder
    @ToString
    class CreateResponse {

        @NotEmpty
        private final String id;

    }

}
