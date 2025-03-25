package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;


public interface PlayGameUseCase {

    PlayResponse play(@NotNull @Valid PlayAction playAction) throws InfrastructureException, DomainException;

    record PlayAction(@NotEmpty String gameId, @NotEmpty String playerReference) {}

    record PlayResponse(@NotNull PlayerView playerView) {}

    record PlayerView(@NotEmpty String reference, @NotEmpty String name, @NotEmpty String lastname,
                      @NotNull Score score) {}

}
