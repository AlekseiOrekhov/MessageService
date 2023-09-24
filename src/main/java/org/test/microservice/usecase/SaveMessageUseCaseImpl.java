package org.test.microservice.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.test.microservice.rabbit.dto.service.MessageService;
import org.test.microservice.usecase.model.Message;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveMessageUseCaseImpl implements SaveMessageUseCase {

    private final MessageService messageService;

    @Override
    @CachePut(cacheNames = "messageByType")
    public List<Message> saveAll(@NotNull List<Message> messageList) {
        List<Message> validMessages = new ArrayList<>();
        for (Message message : messageList) {
            if (validate(message)) {
                messageService.save(message);
                validMessages.add(message);
            }
        }
        return validMessages;
    }

    private boolean validate(Message message) {
        return message.getType() != null;
    }
}
