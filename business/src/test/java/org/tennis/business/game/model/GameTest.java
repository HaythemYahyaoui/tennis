package org.tennis.business.game.model;

import org.junit.jupiter.api.Test;
import org.tennis.business.exception.DomainException;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {


    @Test
    void test_player_scores_point_when_at_love() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        Player updatedPlayer = game.play("p1");

        assertEquals(Score.FIFTEEN, updatedPlayer.getScore());
        assertEquals(Score.LOVE, playerTwo.getScore());
    }

    @Test
    void test_game_throws_exception_when_playing_after_game_ended() throws NoSuchFieldException, IllegalAccessException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");

        Field scoreField = Player.class.getDeclaredField("score");
        scoreField.setAccessible(true);
        scoreField.set(playerOne, Score.GAME);

        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        DomainException exception = assertThrows(DomainException.class, () -> {
            game.play("p2");
        });

        assertEquals("Game has already ended", exception.getMessage());
    }

    @Test
    void player_scores_point_at_fifteen() throws DomainException {
        Player playerOne = new Player("1", "John", "Doe");
        Player playerTwo = new Player("2", "Jane", "Doe");
        Game game = Game.builder().id("game1").playerOne(playerOne).playerTow(playerTwo).build();

        playerOne.updateScore(Score.FIFTEEN);
        game.play("1");

        assertEquals(Score.THIRTY, playerOne.getScore());
    }

    @Test
    void player_scores_point_at_thirty_other_not_forty() throws DomainException {
        Player playerOne = new Player("1", "John", "Doe");
        Player playerTwo = new Player("2", "Jane", "Doe");
        Game game = Game.builder().id("game1").playerOne(playerOne).playerTow(playerTwo).build();

        playerOne.updateScore(Score.THIRTY);
        playerTwo.updateScore(Score.FIFTEEN);
        game.play("1");

        assertEquals(Score.FORTY, playerOne.getScore());
    }

    @Test
    void player_scores_point_at_forty_wins_game() throws DomainException {
        Player playerOne = new Player("1", "John", "Doe");
        Player playerTwo = new Player("2", "Jane", "Doe");
        Game game = Game.builder().id("game1").playerOne(playerOne).playerTow(playerTwo).build();

        playerOne.updateScore(Score.FORTY);
        game.play("1");

        assertEquals(Score.GAME, playerOne.getScore());
        assertTrue(game.isEnded());
    }

    @Test
    void test_player_scores_point_when_at_fifteen() throws DomainException {
        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.FIFTEEN);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        Player updatedPlayer = game.play("p1");


        assertEquals(Score.THIRTY, updatedPlayer.getScore());
        assertEquals(Score.LOVE, playerTwo.getScore());
    }

    @Test
    void test_player_scores_point_when_at_thirty_and_other_not_at_forty() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.THIRTY);
        playerTwo.updateScore(Score.THIRTY);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        Player updatedPlayer = game.play("p1");


        assertEquals(Score.FORTY, updatedPlayer.getScore());
        assertEquals(Score.THIRTY, playerTwo.getScore());
    }

    @Test
    void test_player_scores_point_when_at_advantage_and_wins() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.ADVANTAGE);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        Player updatedPlayer = game.play("p1");


        assertEquals(Score.GAME, updatedPlayer.getScore());
        assertEquals(Score.LOVE, playerTwo.getScore());
    }

    @Test
    void test_player_at_thirty_scores_against_player_at_forty_resulting_in_deuce() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.THIRTY);
        playerTwo.updateScore(Score.FORTY);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        Player updatedPlayer = game.play("p1");

        assertEquals(Score.DEUCE, updatedPlayer.getScore());
        assertEquals(Score.DEUCE, playerTwo.getScore());
    }

    @Test
    void test_player_scores_advantage_when_at_deuce() throws DomainException {
        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.DEUCE);
        playerTwo.updateScore(Score.DEUCE);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        Player updatedPlayer = game.play("p1");

        assertEquals(Score.ADVANTAGE, updatedPlayer.getScore());
        assertEquals(Score.DEUCE, playerTwo.getScore());
    }

    @Test
    void test_game_throws_exception_for_invalid_player_reference() {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        assertThrows(DomainException.class, () -> {
            game.play("invalid_reference");
        });
    }

    @Test
    void test_player_at_thirty_scores_against_player_at_deuce() {
        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.THIRTY);
        playerTwo.updateScore(Score.DEUCE);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> {
            game.play("p1");
        });
        assertEquals("Invalid score : can't happen !", exception.getMessage());
    }


    @Test
    void test_game_throws_exception_on_invalid_score_state() {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        playerOne.updateScore(Score.GAME);
        playerTwo.updateScore(Score.ADVANTAGE);


        assertThrows(DomainException.class, () -> {
            game.play("p1");
        });
    }

    @Test
    void test_game_returns_correct_player_after_scoring() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        Player updatedPlayer = game.play("p1");

        assertEquals(playerOne, updatedPlayer);
        assertEquals(Score.FIFTEEN, updatedPlayer.getScore());
        assertEquals(Score.LOVE, playerTwo.getScore());
    }

    @Test
    void test_game_tracks_scores_across_multiple_plays() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();

        Player updatedPlayer = game.play("p1");
        assertEquals(Score.FIFTEEN, updatedPlayer.getScore());
        assertEquals(Score.LOVE, playerTwo.getScore());

        updatedPlayer = game.play("p2");
        assertEquals(Score.FIFTEEN, updatedPlayer.getScore());
        assertEquals(Score.FIFTEEN, playerOne.getScore());

        updatedPlayer = game.play("p1");
        assertEquals(Score.THIRTY, updatedPlayer.getScore());
        assertEquals(Score.FIFTEEN, playerTwo.getScore());

        updatedPlayer = game.play("p1");
        assertEquals(Score.FORTY, updatedPlayer.getScore());
        assertEquals(Score.FIFTEEN, playerTwo.getScore());

        updatedPlayer = game.play("p1");
        assertEquals(Score.GAME, updatedPlayer.getScore());
        assertTrue(game.isEnded());
    }

    @Test
    void test_deuce_scenario_state_consistency() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.THIRTY);
        playerTwo.updateScore(Score.FORTY);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        game.play("p1");


        assertEquals(Score.DEUCE, playerOne.getScore());
        assertEquals(Score.DEUCE, playerTwo.getScore());
    }

    @Test
    void test_game_ends_when_player_reaches_game_score() throws DomainException {

        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");
        playerOne.updateScore(Score.FORTY);
        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        Player updatedPlayer = game.play("p1");


        assertEquals(Score.GAME, updatedPlayer.getScore());
        assertTrue(game.isEnded());
    }

    @Test
    void test_game_builder_initialization() {
        Player playerOne = new Player("p1", "John", "Doe");
        Player playerTwo = new Player("p2", "Jane", "Doe");

        Game game = Game.builder()
                .id("game1")
                .playerOne(playerOne)
                .playerTow(playerTwo)
                .build();


        assertEquals("game1", game.getId());
        assertEquals(playerOne, game.getPlayerOne());
        assertEquals(playerTwo, game.getPlayerTow());
    }
}