package org.tennis.infrastructure.mongo.game;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameMongoRepositoryImpl extends MongoRepository<GameEntity, String> {
}
