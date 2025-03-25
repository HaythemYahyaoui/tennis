package org.tennis.business.game.port.input.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.port.input.CreateGameUseCase;
import org.tennis.business.game.port.output.GameEvent;
import org.tennis.business.game.port.output.GameRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameEvent gameEvent;

    @Mock
    private CreateGameUseCaseImpl.PlayerMapper playerMapper;

    private CreateGameUseCaseImpl createGameUseCase;

    private CreateGameUseCase.CreateAction validCreateAction;
    private Player playerOne;
    private Player playerTow;
    private Game game;
    private Game savedGame;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        createGameUseCase = new CreateGameUseCaseImpl(gameRepository, gameEvent);

        validCreateAction = new CreateGameUseCase.CreateAction(new CreateGameUseCase.PlayerDto("playerOne", "playerOne", "playerOne"),
                new CreateGameUseCase.PlayerDto("playerTow", "playerTow", "playerTow"));
        playerOne = new Player("playerOne", "playerOne", "playerOne");
        playerTow = new Player("playerTow", "playerTow", "playerTow");

        game = Game.builder().id("1L").playerOne(playerOne).playerTow(playerTow).build();
        savedGame = Game.builder().id("1L").playerOne(playerOne).playerTow(playerTow).build();

        Mockito.when(gameRepository.save(any())).thenReturn(savedGame);
    }

    @Test
    void testCreateGame_success() throws InfrastructureException {

        CreateGameUseCase.CreateResponse response = createGameUseCase.create(validCreateAction);

        assertNotNull(response);
        assertEquals(savedGame.getId(), response.id());
        verify(gameRepository, times(1)).save(any());
        verify(gameEvent, times(1)).gameCreated(any(GameEvent.GameCreatedEvent.class));
    }


}
