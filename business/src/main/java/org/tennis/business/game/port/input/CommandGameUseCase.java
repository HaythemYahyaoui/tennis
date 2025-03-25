package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;

public interface CommandGameUseCase {

    FindGameUseCase.GameView run(@NotNull @Valid CommandAction commandAction) throws InfrastructureException, DomainException;

    record CommandAction(@NotEmpty @Size(min = 2) String command) {}

}
