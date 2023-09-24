package org.test.microservice.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.test.microservice.config.CachingConfig;
import org.test.microservice.config.LibraryForTestConfig;
import org.test.microservice.config.ObjectMapperConfig;
import org.test.microservice.config.RabbitMQConfig;
import org.test.microservice.database.entity.MessageEntity;
import org.test.microservice.database.repository.MessageRepository;
import org.test.microservice.en.MessageType;
import org.test.microservice.scheduler.CountMessageScheduler;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("local")
@Import({ObjectMapperConfig.class, LibraryForTestConfig.class, CachingConfig.class, RabbitMQConfig.class, RabbitMQConsumer.class, LibraryForTestConfig.class})
@EnableRabbit
@SpringBootTest
@MockBean(CountMessageScheduler.class)
class RabbitMQConsumerTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RabbitMQConsumer rabbitMQConsumer;

    @Test
    void validateCorrectJSONAndSaveToDB() throws Exception {
        String json = "[\n" +
                "    {\n" +
                "        \"from\":\"ami\",\n" +
                "        \"to\":\"john\",\n" +
                "        \"text\":\"privet\",\n" +
                "        \"type\":\"EMAIL\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"from\":\"Nina\",\n" +
                "        \"to\":\"Kevin\",\n" +
                "        \"text\":\"poka\",\n" +
                "        \"type\":\"SMS\"\n" +
                "    }\n" +
                "]\n";

        rabbitMQConsumer.consume(json);

        List<MessageEntity> messageEntities = messageRepository.findAll();
        assertThat(messageEntities.size()).isEqualTo(2);
        for (MessageEntity message : messageEntities) {
            if (message.getType().equals(MessageType.SMS)) {
                assertThat(message).extracting(MessageEntity::getFrom).isEqualTo("Nina");
                assertThat(message).extracting(MessageEntity::getTo).isEqualTo("Kevin");
                assertThat(message).extracting(MessageEntity::getText).isEqualTo("poka");
            } else if (message.getType().equals(MessageType.EMAIL)) {
                assertThat(message).extracting(MessageEntity::getFrom).isEqualTo("ami");
                assertThat(message).extracting(MessageEntity::getTo).isEqualTo("john");
                assertThat(message).extracting(MessageEntity::getText).isEqualTo("privet");
            }
        }
    }

    @Test
    void validateCorrectJSONWithIncorrectTypeInOneOfTheMessagesAndSaveToDBOnlyCorrectMessages() throws Exception {
        String json = "[\n" +
                "    {\n" +
                "        \"from\":\"ami\",\n" +
                "        \"to\":\"john\",\n" +
                "        \"text\":\"privet\",\n" +
                "        \"type\":\"EMAIL\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"from\":\"Nina\",\n" +
                "        \"to\":\"Kevin\",\n" +
                "        \"text\":\"poka\",\n" +
                "        \"type\":\"SMS\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"from\":\"ami\",\n" +
                "        \"to\":\"john\",\n" +
                "        \"text\":\"privet\",\n" +
                "        \"type\":\"WHATSAPP\"\n" +
                "    }\n" +
                "]\n";

        rabbitMQConsumer.consume(json);

        List<MessageEntity> messageEntities = messageRepository.findAll();
        assertThat(messageEntities.size()).isEqualTo(4);
        for (MessageEntity message : messageEntities) {
            if (message.getType().equals(MessageType.SMS)) {
                assertThat(message).extracting(MessageEntity::getFrom).isEqualTo("Nina");
                assertThat(message).extracting(MessageEntity::getTo).isEqualTo("Kevin");
                assertThat(message).extracting(MessageEntity::getText).isEqualTo("poka");
            } else if (message.getType().equals(MessageType.EMAIL)) {
                assertThat(message).extracting(MessageEntity::getFrom).isEqualTo("ami");
                assertThat(message).extracting(MessageEntity::getTo).isEqualTo("john");
                assertThat(message).extracting(MessageEntity::getText).isEqualTo("privet");
            }
        }
    }

    @Test
    void JSONIsInvalidAndNoSavedMessages() throws Exception {
        String json = "[\n" +
                "    {\n" +
                "        \"from\":\"ami\",\n" +
                "        \"to\":\"john\",\n" +
                "        \"text\":\"privet\",\n" +
                "        \"type\":\"EMAIL\"\n" +
                "    }},\n" +
                "    {\n" +
                "        \"from\":\"Nina\",\n" +
                "        \"to\":\"Kevin\",\n" +
                "        \"text\":\"poka\",\n" +
                "        \"type\":\"SMS\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"from\":\"ami\",\n" +
                "        \"to\":\"john\",\n" +
                "        \"text\":\"privet\",\n" +
                "        \"type\":\"WHATSAPP\"\n" +
                "    }\n" +
                "]\n";

        rabbitMQConsumer.consume(json);

        List<MessageEntity> messageEntities = messageRepository.findAll();
        assertThat(messageEntities.size()).isEqualTo(4);
    }
}