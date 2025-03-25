package org.tennis.business.game.port.input.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.port.input.CommandGameUseCase;
import org.tennis.business.game.port.input.CreateGameUseCase;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.input.PlayGameUseCase;
import org.tennis.business.utils.UseCase;

@Validated
@UseCase(id = "JIRA-004",
        name = "Run Command Game",
        description = "Run a command game",
        uses = {"JIRA-001", "JIRA-002", "JIRA-003"})
public class CommandGameUseCaseImpl implements CommandGameUseCase {

    private final CreateGameUseCase createGameUseCase;
    private final FindGameUseCase findGameUseCase;
    private final PlayGameUseCase playGameUseCase;

    public CommandGameUseCaseImpl(@NotNull CreateGameUseCase createGameUseCase, @NotNull FindGameUseCase findGameUseCase, @NotNull PlayGameUseCase playGameUseCase) {
        this.createGameUseCase = createGameUseCase;
        this.findGameUseCase = findGameUseCase;
        this.playGameUseCase = playGameUseCase;
    }

    @Override
    public FindGameUseCase.GameView run(@NotNull @Valid CommandAction commandAction) throws InfrastructureException, DomainException {
        CreateGameUseCase.CreateResponse createResponse = createGame(commandAction);
        runGame(commandAction, createResponse);
        return getGameView(createResponse);
    }

    private CreateGameUseCase.CreateResponse createGame(CommandAction commandAction) throws InfrastructureException {
        String[] commandSplited = commandAction.command().split("");
        String playerOneReference = commandSplited[0];
        String playerTowReference = commandSplited[1];

        CreateGameUseCase.PlayerDto playerOneDto = new CreateGameUseCase.PlayerDto(playerOneReference, playerOneReference, playerOneReference);

        CreateGameUseCase.PlayerDto playerTowDto = new CreateGameUseCase.PlayerDto(playerTowReference, playerTowReference, playerTowReference);
        return createGameUseCase.create(new CreateGameUseCase.CreateAction(playerOneDto, playerTowDto));
    }

    private void runGame(CommandAction commandAction, CreateGameUseCase.CreateResponse createResponse) throws InfrastructureException, DomainException {
        String[] commandSplited = commandAction.command().split("");
        int i = 0;
        while (i < commandSplited.length) {
            String playerWhoWon = commandSplited[i];
            playGameUseCase.play(new PlayGameUseCase.PlayAction(createResponse.id(), playerWhoWon));
            i++;
        }
    }

    private FindGameUseCase.GameView getGameView(CreateGameUseCase.CreateResponse createResponse) throws InfrastructureException {
        return findGameUseCase.findGame(new FindGameUseCase.FindGameAction(createResponse.id()));
    }

}
