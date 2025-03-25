package org.tennis.application.restfull.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.port.input.CommandGameUseCase;
import org.tennis.business.game.port.input.CreateGameUseCase;
import org.tennis.business.game.port.input.FindGameUseCase;
import org.tennis.business.game.port.input.PlayGameUseCase;

import java.net.URI;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final PlayGameUseCase playGameUseCase;
    private final FindGameUseCase findGameUseCase;
    private final CommandGameUseCase commandGameUseCase;

    public GameController(CreateGameUseCase createGameUseCase, PlayGameUseCase playGameUseCase, FindGameUseCase findGameUseCase, CommandGameUseCase commandGameUseCase) {
        this.createGameUseCase = createGameUseCase;
        this.playGameUseCase = playGameUseCase;
        this.findGameUseCase = findGameUseCase;
        this.commandGameUseCase = commandGameUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody CreateGameUseCase.CreateAction createAction) throws InfrastructureException {
        CreateGameUseCase.CreateResponse createResponse = createGameUseCase.create(createAction);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/game/{id}").buildAndExpand(createResponse.id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public PlayGameUseCase.PlayResponse play(@RequestBody PlayGameUseCase.PlayAction playAction) throws InfrastructureException, DomainException {
        return playGameUseCase.play(playAction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindGameUseCase.GameView get(@PathVariable(value = "id") String id) throws InfrastructureException {
        return findGameUseCase.findGame(new FindGameUseCase.FindGameAction(id));
    }

    @PostMapping("/run/{command}")
    @ResponseStatus(HttpStatus.CREATED)
    public FindGameUseCase.GameView run(@PathVariable(value = "command") String command) throws InfrastructureException, DomainException {
        return commandGameUseCase.run(new CommandGameUseCase.CommandAction(command));
    }

}