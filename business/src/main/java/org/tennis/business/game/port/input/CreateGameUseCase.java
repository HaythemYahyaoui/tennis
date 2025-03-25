package org.tennis.business.game.port.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.tennis.business.exception.InfrastructureException;


public interface CreateGameUseCase {

    CreateResponse create(@NotNull @Valid CreateAction createAction) throws InfrastructureException;

    record CreateAction(@NotNull @Valid PlayerDto playerOne, @NotNull @Valid PlayerDto playerTow) {}

    record PlayerDto(@NotEmpty String reference, @NotEmpty String name, @NotEmpty String lastname) {}

    record CreateResponse(@NotEmpty String id) {}
}
