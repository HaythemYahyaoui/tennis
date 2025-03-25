package org.tennis.business.game.port.input.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Game;
import org.tennis.business.game.model.Player;
import org.tennis.business.game.port.input.PlayGameUseCase;
import org.tennis.business.game.port.output.GameEvent;
import org.tennis.business.game.port.output.GameRepository;
import org.tennis.business.utils.UseCase;

@Validated
@UseCase(id = "JIRA-003",
        name = "Play Game",
        description = "Play a game by id and player reference")
public class PlayGameUseCaseImpl implements PlayGameUseCase {

    private final GameRepository gameRepository;
    private final GameEvent gameEvent;

    public PlayGameUseCaseImpl(@NotNull GameRepository gameRepository, @NotNull GameEvent gameEvent) {
        this.gameRepository = gameRepository;
        this.gameEvent = gameEvent;
    }


    @Override
    public PlayResponse play(@NotNull @Valid PlayAction playAction) throws InfrastructureException, DomainException {
        Game game = gameRepository.findById(playAction.gameId());
        Player player = game.play(playAction.playerReference());
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
        return new PlayResponse(new PlayerView(player.getReference(), player.getName(), player.getLastname(), player.getScore()));
    }

}
