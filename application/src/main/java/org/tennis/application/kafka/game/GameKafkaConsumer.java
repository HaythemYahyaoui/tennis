package org.tennis.application.kafka.game;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.tennis.business.exception.InfrastructureException;
import org.tennis.business.game.model.Score;
import org.tennis.business.game.port.output.GameEvent;

@Slf4j
@Service
public class GameKafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.game}", groupId = "my-group")
    public void listenGameCreated(ConsumerRecord<String, Object> consumerRecord) throws InfrastructureException {
        if (consumerRecord.value() instanceof GameEvent.GameCreatedEvent gameCreatedEvent) {
            log.info("Game started Player {} vs Player {}",
                    gameCreatedEvent.getPlayerOneReference(),
                    gameCreatedEvent.getPlayerTowReference()
            );
        } else if (consumerRecord.value() instanceof GameEvent.PlayDoneEvent playDoneEvent) {
            log.info("Player {} : {} / Player {} : {} ",
                    playDoneEvent.getPlayerOneReference(),
                    playDoneEvent.getPlayerOneScore(),
                    playDoneEvent.getPlayerTowReference(),
                    playDoneEvent.getPlayerTowScore()
            );
        } else if (consumerRecord.value() instanceof GameEvent.GameEndedEvent gameEndedEvent) {
            log.info("Player {} win the game",
                    Score.GAME.equals(gameEndedEvent.getPlayerOneScore()) ?
                            gameEndedEvent.getPlayerOneReference() :
                            gameEndedEvent.getPlayerTowReference()
            );
        }
    }

}