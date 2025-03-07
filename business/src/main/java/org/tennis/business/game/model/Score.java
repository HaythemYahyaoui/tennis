package org.tennis.business.game.model;


public enum Score {

    LOVE(0, "LOVE"),
    FIFTEEN(1, "15"),
    THIRTY(2, "30"),
    FORTY(3, "40"),
    DEUCE(4, "DEUCE"),
    ADVANTAGE(5, "ADVANTAGE"),
    GAME(6, "GAME");

    private final int points;
    private final String displayName;

    Score(int points, String displayName) {
        this.points = points;
        this.displayName = displayName;
    }

    public int getPoints() {
        return points;
    }

    public static Score fromPoints(int points) {
        for (Score score : values()) {
            if (score.getPoints() == points) {
                return score;
            }
        }
        throw new IllegalArgumentException("Invalid point count: " + points);
    }

    @Override
    public String toString() {
        return displayName;
    }



}
