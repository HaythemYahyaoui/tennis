package org.tennis.business.game.port.output;

import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;

public interface GameRepository {

    Game save(Game game) throws InfrastructureException;

    Game findById(String id) throws InfrastructureException;
}
