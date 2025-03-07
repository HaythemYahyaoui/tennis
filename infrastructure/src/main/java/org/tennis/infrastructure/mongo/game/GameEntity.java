package org.tennis.infrastructure.mongo.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;

@Data
@Document
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameEntity {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private Player playerOne;
    private Player playerTow;

    @org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
    public interface Mapper {
        GameEntity gameEntityFromGame(Game game);

        Game gameFromGameEntity(GameEntity gameEntity);
    }

}
