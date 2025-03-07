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

        validCommandAction = new CommandGameUseCase.CommandAction();
        validCommandAction.setCommand("ABABAA");

        CreateGameUseCase.CreateResponse createResponse = CreateGameUseCase.CreateResponse.builder().id("1L").build();

        when(createGameUseCase.create(any())).thenReturn(createResponse);
        when(findGameUseCase.findGame(any())).thenReturn(FindGameUseCase.GameView.builder().id("1L")
                .playerOne(FindGameUseCase.PlayerDto.builder().reference("A").name("A").lastname("A").score(Score.GAME).build())
                .playerTow(FindGameUseCase.PlayerDto.builder().reference("B").name("B").lastname("B").score(Score.THIRTY).build()).build());
    }

    @Test
    void testRun_success() throws InfrastructureException, DomainException {
        commandGameUseCase.run(validCommandAction);
        verify(createGameUseCase, times(1)).create(any());
        verify(playGameUseCase, times(6)).play(any());
        verify(findGameUseCase, times(1)).findGame(any());
    }

}
