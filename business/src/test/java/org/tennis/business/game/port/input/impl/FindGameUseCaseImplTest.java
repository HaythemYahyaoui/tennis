package org.tennis.business.game.port.input.impl;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.model.Score;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.output.GameRepository;
import org.tennis.business.utils.UseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FindGameUseCaseImplTest {

    @Test
    public void test_find_game_returns_mapped_game_view() throws InfrastructureException {
        String gameId = "game123";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        Player playerOne = new Player("player1", "Alice", "Johnson");
        Player playerTwo = new Player("player2", "Bob", "Brown");
        playerTwo.updateScore(Score.FIFTEEN);

        Game game = Game.builder()
                .id(gameId)
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(game);

        FindGameUseCase.GameView result = findGameUseCase.findGame(new FindGameUseCase.FindGameAction(gameId));

        assertNotNull(result);
        assertEquals(gameId, result.id());
        assertEquals(playerOne.getReference(), result.playerOne().reference());
        assertEquals(playerOne.getName(), result.playerOne().name());
        assertEquals(playerOne.getLastname(), result.playerOne().lastname());
        assertEquals(playerOne.getScore(), result.playerOne().score());
        assertEquals(playerTwo.getReference(), result.playerTow().reference());
        assertEquals(playerTwo.getName(), result.playerTow().name());
        assertEquals(playerTwo.getLastname(), result.playerTow().lastname());
        assertEquals(playerTwo.getScore(), result.playerTow().score());

        verify(gameRepository).findById(gameId);
    }

    @Test
    public void test_find_game_with_null_action_throws_exception() {

        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);


        assertThrows(NullPointerException.class, () -> findGameUseCase.findGame(null));
    }


    @Test
    @SneakyThrows
    public void test_find_game_propagates_infrastructure_exception() {

        String gameId = "game123";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        InfrastructureException infrastructureException = new InfrastructureException("Database error");
        when(gameRepository.findById(gameId)).thenThrow(infrastructureException);

        InfrastructureException thrownException = assertThrows(InfrastructureException.class, () -> {
            findGameUseCase.findGame(new FindGameUseCase.FindGameAction(gameId));
        });

        assertEquals("Database error", thrownException.getMessage());
        verify(gameRepository).findById(gameId);
    }


    @Test
    @SneakyThrows
    public void test_find_game_throws_infrastructure_exception_for_nonexistent_id() {

        String nonExistentGameId = "nonExistentGameId";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        when(gameRepository.findById(nonExistentGameId)).thenThrow(new InfrastructureException("Game not found"));


        assertThrows(InfrastructureException.class, () -> {
            findGameUseCase.findGame(new FindGameUseCase.FindGameAction(nonExistentGameId));
        });

        verify(gameRepository).findById(nonExistentGameId);
    }

    @Test
    public void test_find_game_action_with_valid_id() throws InfrastructureException {

        String validGameId = "validGame123";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        Player playerOne = new Player("player1", "Alice", "Johnson");
        Player playerTwo = new Player("player2", "Bob", "Brown");
        playerTwo.updateScore(Score.THIRTY);

        Game game = Game.builder()
                .id(validGameId)
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        when(gameRepository.findById(validGameId)).thenReturn(game);


        FindGameUseCase.GameView result = findGameUseCase.findGame(new FindGameUseCase.FindGameAction(validGameId));

        assertNotNull(result);
        assertEquals(validGameId, result.id());
        assertEquals(playerOne.getReference(), result.playerOne().reference());
        assertEquals(playerOne.getName(), result.playerOne().name());
        assertEquals(playerOne.getLastname(), result.playerOne().lastname());
        assertEquals(playerOne.getScore(), result.playerOne().score());
        assertEquals(playerTwo.getReference(), result.playerTow().reference());
        assertEquals(playerTwo.getName(), result.playerTow().name());
        assertEquals(playerTwo.getLastname(), result.playerTow().lastname());
        assertEquals(playerTwo.getScore(), result.playerTow().score());

        verify(gameRepository).findById(validGameId);
    }


    @Test
    public void test_game_mapper_maps_game_to_game_view_correctly() throws InfrastructureException {

        String gameId = "game123";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        Player playerOne = new Player("player1", "Alice", "Johnson");
        Player playerTwo = new Player("player2", "Bob", "Brown");
        playerTwo.updateScore(Score.FIFTEEN);

        Game game = Game.builder()
                .id(gameId)
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(game);


        FindGameUseCase.GameView result = findGameUseCase.findGame(new FindGameUseCase.FindGameAction(gameId));


        assertNotNull(result);
        assertEquals(gameId, result.id());
        assertEquals(playerOne.getReference(), result.playerOne().reference());
        assertEquals(playerOne.getName(), result.playerOne().name());
        assertEquals(playerOne.getLastname(), result.playerOne().lastname());
        assertEquals(playerOne.getScore(), result.playerOne().score());
        assertEquals(playerTwo.getReference(), result.playerTow().reference());
        assertEquals(playerTwo.getName(), result.playerTow().name());
        assertEquals(playerTwo.getLastname(), result.playerTow().lastname());
        assertEquals(playerTwo.getScore(), result.playerTow().score());

        verify(gameRepository).findById(gameId);
    }

    @Test
    public void test_game_with_all_player_data_is_properly_mapped_to_dto() throws InfrastructureException {

        String gameId = "game123";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        Player playerOne = new Player("player1", "Alice", "Johnson");
        Player playerTwo = new Player("player2", "Bob", "Brown");
        playerTwo.updateScore(Score.FIFTEEN);

        Game game = Game.builder()
                .id(gameId)
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(game);


        FindGameUseCase.GameView result = findGameUseCase.findGame(new FindGameUseCase.FindGameAction(gameId));

        assertNotNull(result);
        assertEquals(gameId, result.id());
        assertEquals(playerOne.getReference(), result.playerOne().reference());
        assertEquals(playerOne.getName(), result.playerOne().name());
        assertEquals(playerOne.getLastname(), result.playerOne().lastname());
        assertEquals(playerOne.getScore(), result.playerOne().score());
        assertEquals(playerTwo.getReference(), result.playerTow().reference());
        assertEquals(playerTwo.getName(), result.playerTow().name());
        assertEquals(playerTwo.getLastname(), result.playerTow().lastname());
        assertEquals(playerTwo.getScore(), result.playerTow().score());

        verify(gameRepository).findById(gameId);
    }

    @Test
    public void test_use_case_annotation_metadata() {
        UseCase useCaseAnnotation = FindGameUseCaseImpl.class.getAnnotation(UseCase.class);
        assertNotNull(useCaseAnnotation);
        assertEquals("JIRA-003", useCaseAnnotation.id());
        assertEquals("Find Game", useCaseAnnotation.name());
        assertEquals("Find a game by id", useCaseAnnotation.description());
    }

    @Test
    public void test_mapper_with_incomplete_game_objects() throws InfrastructureException {
        String gameId = "incompleteGame";
        GameRepository gameRepository = mock(GameRepository.class);
        FindGameUseCaseImpl findGameUseCase = new FindGameUseCaseImpl(gameRepository);

        Player playerOne = new Player("player1", "Alice", "Johnson");
        Player playerTwo = new Player("player2", "Bob", "Brown");

        Game incompleteGame = Game.builder()
                .id(gameId)
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(incompleteGame);

        FindGameUseCase.GameView result = findGameUseCase.findGame(new FindGameUseCase.FindGameAction(gameId));

        assertNotNull(result);
        assertEquals(gameId, result.id());
        assertEquals(playerOne.getReference(), result.playerOne().reference());
        assertEquals(playerOne.getName(), result.playerOne().name());
        assertEquals(playerOne.getLastname(), result.playerOne().lastname());
        assertEquals(playerOne.getScore(), result.playerOne().score());
        assertEquals(playerTwo.getReference(), result.playerTow().reference());
        assertEquals(playerTwo.getName(), result.playerTow().name());
        assertEquals(playerTwo.getLastname(), result.playerTow().lastname());

        verify(gameRepository).findById(gameId);
    }
}