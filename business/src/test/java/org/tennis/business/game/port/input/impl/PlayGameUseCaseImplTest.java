package org.tennis.business.game.port.input.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.port.input.PlayGameUseCase;
import org.tennis.business.game.port.output.GameEvent;
import org.tennis.business.game.port.output.GameRepository;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayGameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameEvent gameEvent;

    @InjectMocks
    private PlayGameUseCaseImpl playGameUseCase;

    private Game mockGame;
    private Player mockPlayer;
    private PlayGameUseCase.PlayAction mockPlayAction;

    @BeforeEach
    void setUp() throws InfrastructureException, DomainException {
        mockGame = mock(Game.class);
        mockPlayer = new Player("P1", "John", "Doe");
        mockPlayAction = new PlayGameUseCase.PlayAction("game-123", "P1");
        when(gameRepository.findById("game-123")).thenReturn(mockGame);
        when(mockGame.play("P1")).thenReturn(mockPlayer);
        when(gameRepository.save(mockGame)).thenReturn(mockGame);
        when(mockGame.getId()).thenReturn("game-123");
        when(mockGame.getPlayerOne()).thenReturn(new Player("P1", "John", "Doe"));
        when(mockGame.getPlayerTow()).thenReturn(new Player("P2", "Jane", "Doe"));
        when(mockGame.isEnded()).thenReturn(false);
    }

    @Test
    void testPlayGame_Success() throws InfrastructureException, DomainException {
        PlayGameUseCase.PlayResponse response = playGameUseCase.play(mockPlayAction);

        assertNotNull(response);
        assertEquals("P1", response.playerView().reference());
        assertEquals("John", response.playerView().name());

        verify(gameRepository).findById("game-123");
        verify(mockGame).play("P1");
        verify(gameRepository).save(mockGame);

        ArgumentCaptor<GameEvent.PlayDoneEvent> eventCaptor = ArgumentCaptor.forClass(GameEvent.PlayDoneEvent.class);
        verify(gameEvent).playDone(eventCaptor.capture());

        GameEvent.PlayDoneEvent capturedEvent = eventCaptor.getValue();
        assertEquals("game-123", capturedEvent.getId());
        assertEquals("P1", capturedEvent.getPlayerOneReference());
    }

    @Test
    void testPlayGame_WhenGameEnds() throws InfrastructureException, DomainException {
        when(mockGame.isEnded()).thenReturn(true);

        playGameUseCase.play(mockPlayAction);

        verify(gameEvent).gameEnded(any(GameEvent.GameEndedEvent.class));
    }


}
