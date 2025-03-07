package org.tennis.business.game.port.input.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.port.input.CreateGameUseCase;
import org.tennis.business.game.port.output.GameEvent;
import org.tennis.business.game.port.output.GameRepository;
import org.tennis.business.utils.UseCase;

import java.util.Objects;

@UseCase(id = "JIRA-001",
        name = "Create Game",
        description = "Create a new game")
public class CreateGameUseCaseImpl implements CreateGameUseCase {

    private final GameRepository gameRepository;
    private final GameEvent gameEvent;
    private final PlayerMapper playerMapper;

    public CreateGameUseCaseImpl(GameRepository gameRepository, GameEvent gameEvent) {
        Objects.requireNonNull(gameRepository);
        Objects.requireNonNull(gameEvent);
        this.gameRepository = gameRepository;
        this.gameEvent = gameEvent;
        playerMapper = Mappers.getMapper(PlayerMapper.class);
    }

    @Override
    public CreateResponse create(@NotNull @Valid CreateAction createAction) throws InfrastructureException {
        Player playerOne = playerMapper.playerFromPlayerDto(createAction.getPlayerOne());
        Player playerTow = playerMapper.playerFromPlayerDto(createAction.getPlayerTow());
        Game game = Game.builder().playerOne(playerOne).playerTow(playerTow).build();
        Game saved = gameRepository.save(game);
        gameEvent.gameCreated(GameEvent.GameCreatedEvent.builder()
                .id(saved.getId())
                .playerOneReference(game.getPlayerOne().getReference())
                .playerOneScore(game.getPlayerOne().getScore())
                .playerTowReference(game.getPlayerTow().getReference())
                .playerTowScore(game.getPlayerTow().getScore())
                .build());
        return CreateResponse.builder().id(saved.getId()).build();
    }

    @Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
    public interface PlayerMapper {
        Player playerFromPlayerDto(PlayerDto playerDto);
    }

}
