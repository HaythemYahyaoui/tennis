package org.tennis.business.game.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void test_enum_values_correctly_initialized() {
        assertEquals(0, Score.LOVE.getPoints());
        assertEquals("LOVE", Score.LOVE.toString());

        assertEquals(1, Score.FIFTEEN.getPoints());
        assertEquals("15", Score.FIFTEEN.toString());

        assertEquals(2, Score.THIRTY.getPoints());
        assertEquals("30", Score.THIRTY.toString());

        assertEquals(3, Score.FORTY.getPoints());
        assertEquals("40", Score.FORTY.toString());

        assertEquals(4, Score.DEUCE.getPoints());
        assertEquals("DEUCE", Score.DEUCE.toString());

        assertEquals(5, Score.ADVANTAGE.getPoints());
        assertEquals("ADVANTAGE", Score.ADVANTAGE.toString());

        assertEquals(6, Score.GAME.getPoints());
        assertEquals("GAME", Score.GAME.toString());
    }

    @Test
    void test_from_points_throws_exception_for_invalid_points() {
        assertEquals(Score.LOVE, Score.fromPoints(0));
        assertEquals(Score.FIFTEEN, Score.fromPoints(1));
        assertEquals(Score.THIRTY, Score.fromPoints(2));
        assertEquals(Score.FORTY, Score.fromPoints(3));
        assertEquals(Score.DEUCE, Score.fromPoints(4));
        assertEquals(Score.ADVANTAGE, Score.fromPoints(5));
        assertEquals(Score.GAME, Score.fromPoints(6));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Score.fromPoints(-1)
        );
        assertEquals("Invalid point count: -1", exception.getMessage());

        exception = assertThrows(
                IllegalArgumentException.class,
                () -> Score.fromPoints(7)
        );
        assertEquals("Invalid point count: 7", exception.getMessage());
    }

    @Test
    void test_from_points_returns_correct_enum() {
        assertEquals(Score.LOVE, Score.fromPoints(0));
        assertEquals(Score.FIFTEEN, Score.fromPoints(1));
        assertEquals(Score.THIRTY, Score.fromPoints(2));
        assertEquals(Score.FORTY, Score.fromPoints(3));
        assertEquals(Score.DEUCE, Score.fromPoints(4));
        assertEquals(Score.ADVANTAGE, Score.fromPoints(5));
        assertEquals(Score.GAME, Score.fromPoints(6));
    }

    @Test
    void test_score_to_string_returns_display_name() {
        assertEquals("LOVE", Score.LOVE.toString());
        assertEquals("15", Score.FIFTEEN.toString());
        assertEquals("30", Score.THIRTY.toString());
        assertEquals("40", Score.FORTY.toString());
        assertEquals("DEUCE", Score.DEUCE.toString());
        assertEquals("ADVANTAGE", Score.ADVANTAGE.toString());
        assertEquals("GAME", Score.GAME.toString());
    }

    @Test
    void test_no_duplicate_point_values() {
        Set<Integer> pointValues = new HashSet<>();
        for (Score score : Score.values()) {
            assertTrue(pointValues.add(score.getPoints()), "Duplicate point value found: " + score.getPoints());
        }
    }

    @Test
    void test_get_points_for_each_score_enum() {
        assertEquals(0, Score.LOVE.getPoints());
        assertEquals(1, Score.FIFTEEN.getPoints());
        assertEquals(2, Score.THIRTY.getPoints());
        assertEquals(3, Score.FORTY.getPoints());
        assertEquals(4, Score.DEUCE.getPoints());
        assertEquals(5, Score.ADVANTAGE.getPoints());
        assertEquals(6, Score.GAME.getPoints());
    }

    @Test
    void test_from_points_boundary_values() {
        assertEquals(Score.LOVE, Score.fromPoints(0));
        assertEquals(Score.GAME, Score.fromPoints(6));
    }


    @Test
    void test_enum_ordering_preserved() {
        Score[] scores = Score.values();
        assertEquals(Score.LOVE, scores[0]);
        assertEquals(Score.FIFTEEN, scores[1]);
        assertEquals(Score.THIRTY, scores[2]);
        assertEquals(Score.FORTY, scores[3]);
        assertEquals(Score.DEUCE, scores[4]);
        assertEquals(Score.ADVANTAGE, scores[5]);
        assertEquals(Score.GAME, scores[6]);
    }

    @Test
    void test_from_points_method() {
        assertEquals(Score.LOVE, Score.fromPoints(0));
        assertEquals(Score.FIFTEEN, Score.fromPoints(1));
        assertEquals(Score.THIRTY, Score.fromPoints(2));
        assertEquals(Score.FORTY, Score.fromPoints(3));
        assertEquals(Score.DEUCE, Score.fromPoints(4));
        assertEquals(Score.ADVANTAGE, Score.fromPoints(5));
        assertEquals(Score.GAME, Score.fromPoints(6));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Score.fromPoints(7);
        });
        assertEquals("Invalid point count: 7", exception.getMessage());
    }
}