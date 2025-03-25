package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;


public interface FindGameUseCase {

    GameView findGame(@NotNull @Valid FindGameAction playAction) throws InfrastructureException;

    record FindGameAction(@NotEmpty String id) {}

    record GameView(@NotEmpty String id, @NotNull @Valid PlayerDto playerOne, @NotNull @Valid PlayerDto playerTow) {}

    record PlayerDto(@NotEmpty String reference, @NotEmpty String name, @NotEmpty String lastname,
                     @NotNull Score score) {}
}
