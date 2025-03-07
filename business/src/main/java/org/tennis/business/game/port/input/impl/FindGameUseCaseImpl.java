package org.tennis.business.game.port.input.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.output.GameRepository;
import org.tennis.business.utils.UseCase;

import java.util.Objects;

@UseCase(id = "JIRA-003",
        name = "Find Game",
        description = "Find a game by id")
public class FindGameUseCaseImpl implements FindGameUseCase {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public FindGameUseCaseImpl(GameRepository gameRepository) {
        Objects.requireNonNull(gameRepository);
        this.gameRepository = gameRepository;
        gameMapper = Mappers.getMapper(GameMapper.class);
    }

    @Override
    public GameView findGame(@NotNull @Valid FindGameUseCase.FindGameAction playAction) throws InfrastructureException {
        return gameMapper.gameViewFromGame(gameRepository.findById(playAction.getId()));
    }

    @Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
    public interface GameMapper {
        GameView gameViewFromGame(Game game);
    }

}
