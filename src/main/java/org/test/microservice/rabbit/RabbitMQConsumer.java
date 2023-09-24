package org.test.microservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.test.library.CalculateService;
import org.test.library.MetricService;
import org.test.microservice.en.MessageType;
import org.test.microservice.usecase.SaveMessageUseCase;
import org.test.microservice.usecase.model.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final ObjectMapper objectMapper;

    private final MetricService metricService;

    private final SaveMessageUseCase saveMessageUseCase;

    private final CalculateService calculateService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String messages) {
        try {
            log.info("Received message -> " + messages);
            List<Message> messageList = objectMapper.readValue(messages, new TypeReference<>() {
            });
            List<Message> savedMessages = saveMessageUseCase.saveAll(messageList);
            Map<String, Long> metrics = new HashMap<>();
            for (Message message : savedMessages) {
                if (metrics.containsKey(message.getType().toString())) {
                    metrics.put(message.getType().toString(), metrics.get(message.getType().toString() + 1L));
                } else {
                    metrics.put(message.getType().toString(), 1L);
                }
            }
            metricService.save(metrics);
            log.info("Count of message without sms is + {}", calculateService.calculateWithoutSms(savedMessages.size(), metrics.getOrDefault(MessageType.SMS.toString(), 0L).intValue()));
        } catch (JsonProcessingException e) {
            log.error("Invalid JSON: " + e);
        }
    }
}
