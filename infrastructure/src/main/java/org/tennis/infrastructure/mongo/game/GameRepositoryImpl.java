package org.tennis.infrastructure.mongo.game;

import org.springframework.stereotype.Repository;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.port.output.GameRepository;

import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private final GameMongoRepositoryImpl gameMongoRepositoryImp;
    private final GameEntity.Mapper gameEntityMapper;

    public GameRepositoryImpl(GameMongoRepositoryImpl gameMongoRepositoryImp, GameEntity.Mapper gameEntityMapper) {
        this.gameMongoRepositoryImp = gameMongoRepositoryImp;
        this.gameEntityMapper = gameEntityMapper;
    }

    @Override
    public Game save(Game game) throws InfrastructureException {
        try {
            GameEntity gameEntity = gameEntityMapper.gameEntityFromGame(game);
            GameEntity save = gameMongoRepositoryImp.save(gameEntity);
            return gameEntityMapper.gameFromGameEntity(save);
        } catch (Exception e) {
            throw new InfrastructureException("Error saving game", e);
        }
    }

    @Override
    public Game findById(String id) throws InfrastructureException {
        try {
            Optional<GameEntity> optionalGameEntity = gameMongoRepositoryImp.findById(id);
            if (optionalGameEntity.isEmpty()) {
                throw new InfrastructureException("Game not found");
            }
            GameEntity gameEntity = optionalGameEntity.get();
            return gameEntityMapper.gameFromGameEntity(gameEntity);
        } catch (Exception e) {
            throw new InfrastructureException("Game not found", e);
        }
    }

}
