package org.tennis.business.game.port.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;

public interface CommandGameUseCase {

    FindGameUseCase.GameView run(CommandAction commandAction) throws InfrastructureException, DomainException;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class CommandAction {

        @Size(min = 2)
        @NotEmpty
        private String command;

    }

}
