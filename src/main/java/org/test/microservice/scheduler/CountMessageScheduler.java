package org.test.microservice.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.test.microservice.database.entity.CustomMessageEntity;
import org.test.microservice.database.repository.MessageRepository;
import org.test.microservice.en.MessageType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountMessageScheduler {
    public static final int EVERY_1_HOUR = 3600000;
    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = EVERY_1_HOUR)
    public void quartz() throws JsonProcessingException {
        List<CustomMessageEntity> messageEntities = new ArrayList<>();

        for (MessageType type: MessageType.values()) {
            CustomMessageEntity messageEntity = new CustomMessageEntity(type, messageRepository.countMessageEntitiesByType(type).longValue());
            messageEntities.add(messageEntity);
        }

        String jsonArray = objectMapper.writeValueAsString(messageEntities);

        try {
            FileWriter file = new FileWriter("statistic+" + System.currentTimeMillis() + ".json");
            file.write(jsonArray);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(jsonArray);

    }
}
