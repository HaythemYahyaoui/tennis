package org.tennis.infrastructure.kafka.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.port.output.GameEvent;


@Service
public class GameKafkaEvent implements GameEvent {

    @Value("${spring.kafka.topic.game}")
    private String gameTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public GameKafkaEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void gameCreated(GameCreatedEvent gameCreatedEvent) throws InfrastructureException {
        try {
            kafkaTemplate.send(gameTopic, gameCreatedEvent.getId(), gameCreatedEvent);
        } catch (Exception e) {
            throw new InfrastructureException("Error sending game created event", e);
        }
    }

    @Override
    public void playDone(PlayDoneEvent playDoneEvent) throws InfrastructureException {
        try {
            kafkaTemplate.send(gameTopic, playDoneEvent.getId(), playDoneEvent);
        } catch (Exception e) {
            throw new InfrastructureException("Error sending done event", e);
        }
    }


    @Override
    public void gameEnded(GameEndedEvent gameEndedEvent) throws InfrastructureException {
        try {
            kafkaTemplate.send(gameTopic, gameEndedEvent.getId(), gameEndedEvent);
        } catch (Exception e) {
            throw new InfrastructureException("Error sending game ended event", e);
        }
    }

}
