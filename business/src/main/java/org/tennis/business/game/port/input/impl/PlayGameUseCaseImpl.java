package org.tennis.business.game.port.input.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.port.input.PlayGameUseCase;
import org.tennis.business.game.port.output.GameEvent;
import org.tennis.business.game.port.output.GameRepository;
import org.tennis.business.utils.UseCase;

import java.util.Objects;

@UseCase(id = "JIRA-003",
        name = "Play Game",
        description = "Play a game by id and player reference")
public class PlayGameUseCaseImpl implements PlayGameUseCase {

    private final GameRepository gameRepository;
    private final GameEvent gameEvent;

    public PlayGameUseCaseImpl(GameRepository gameRepository, GameEvent gameEvent) {
        Objects.requireNonNull(gameRepository);
        Objects.requireNonNull(gameEvent);
        this.gameRepository = gameRepository;
        this.gameEvent = gameEvent;
    }


    @Override
    public PlayResponse play(@NotNull @Valid PlayAction playAction) throws InfrastructureException, DomainException {
        Game game = gameRepository.findById(playAction.getGameId());
        Player player = game.play(playAction.getPlayerReference());
        Game saved = gameRepository.save(game);
        gameEvent.playDone(GameEvent.PlayDoneEvent.builder()
                .id(saved.getId())
                .playerOneReference(saved.getPlayerOne().getReference())
                .playerOneScore(saved.getPlayerOne().getScore())
                .playerTowReference(saved.getPlayerTow().getReference())
                .playerTowScore(saved.getPlayerTow().getScore())
                .build());
        if (saved.isEnded()) {
            gameEvent.gameEnded(GameEvent.GameEndedEvent.builder()
                    .id(saved.getId())
                    .playerOneReference(saved.getPlayerOne().getReference())
                    .playerOneScore(saved.getPlayerOne().getScore())
                    .playerTowReference(saved.getPlayerTow().getReference())
                    .playerTowScore(saved.getPlayerTow().getScore())
                    .build());
        }
        return PlayResponse.builder().playerView(PlayerView.builder().reference(player.getReference()).name(player.getName()).lastname(player.getLastname()).score(player.getScore()).build()).build();
    }

}
