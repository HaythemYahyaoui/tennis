package org.tennis.business.game.port.input.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.output.GameRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FindGameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private FindGameUseCaseImpl.GameMapper gameMapper;

    @InjectMocks
    private FindGameUseCaseImpl findGameUseCase;

    private FindGameUseCase.FindGameAction validFindGameAction;
    private Game game;
    private FindGameUseCase.GameView gameView;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        validFindGameAction = new FindGameUseCase.FindGameAction();
        validFindGameAction.setId("1L");
        game = Game.builder().id("1L").build();
        gameView = FindGameUseCase.GameView.builder().id("1L").build();
        Mockito.when(gameRepository.findById(validFindGameAction.getId())).thenReturn(game);
    }

    @Test
    void testFindGame_success() throws InfrastructureException {
        FindGameUseCase.GameView result = findGameUseCase.findGame(validFindGameAction);
        assertNotNull(result);
        assertEquals(gameView.getId(), result.getId());
    }

}
