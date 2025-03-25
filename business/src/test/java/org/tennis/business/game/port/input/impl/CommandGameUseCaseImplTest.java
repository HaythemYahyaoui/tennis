package org.tennis.business.game.port.input.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;
import org.tennis.business.game.port.input.CommandGameUseCase;
import org.tennis.business.game.port.input.CreateGameUseCase;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.input.PlayGameUseCase;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandGameUseCaseImplTest {

    @Mock
    private CreateGameUseCase createGameUseCase;

    @Mock
    private FindGameUseCase findGameUseCase;

    @Mock
    private PlayGameUseCase playGameUseCase;

    private CommandGameUseCaseImpl commandGameUseCase;

    private CommandGameUseCase.CommandAction validCommandAction;

    @BeforeEach
    void setUp() throws InfrastructureException {
        commandGameUseCase = new CommandGameUseCaseImpl(createGameUseCase, findGameUseCase, playGameUseCase);

        validCommandAction = new CommandGameUseCase.CommandAction("ABABAA");

        CreateGameUseCase.CreateResponse createResponse = new CreateGameUseCase.CreateResponse("1L");

        when(createGameUseCase.create(any())).thenReturn(createResponse);
        FindGameUseCase.GameView gameView = new FindGameUseCase.GameView("1L", new FindGameUseCase.PlayerDto("A", "A", "A", Score.GAME),
                new FindGameUseCase.PlayerDto("B", "B", "B", Score.THIRTY));
        when(findGameUseCase.findGame(any())).thenReturn(gameView);
    }

    @Test
    void testRun_success() throws InfrastructureException, DomainException {
        commandGameUseCase.run(validCommandAction);
        verify(createGameUseCase, times(1)).create(any());
        verify(playGameUseCase, times(6)).play(any());
        verify(findGameUseCase, times(1)).findGame(any());
    }

}
