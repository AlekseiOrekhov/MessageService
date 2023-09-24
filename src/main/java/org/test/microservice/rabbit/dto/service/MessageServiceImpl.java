package org.test.microservice.rabbit.dto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.test.microservice.database.entity.MessageEntity;
import org.test.microservice.database.repository.MessageRepository;
import org.test.microservice.en.MessageType;
import org.test.microservice.usecase.model.Message;
import org.test.microservice.usecase.model.mapper.MessageMapStructMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapStructMapper messageMapStructMapper;

    @Override
    public List<Message> getAll() {
        return messageMapStructMapper.map(messageRepository.findAll());
    }

    @Override
    public Message getById(long id) {
        Optional<MessageEntity> messageEntityOpt = messageRepository.findAllById(id);
        return messageEntityOpt.map(messageMapStructMapper::map).orElse(null);
    }

    @Override
    public List<Message> getByType(MessageType type) {
        List<MessageEntity> messageEntities = messageRepository.findAllByType(type);
        return messageMapStructMapper.map(messageEntities);
    }

    @Override
    @CacheEvict(cacheNames = "messageByType", allEntries = true)
    public void save(Message message) {
        messageRepository.save(messageMapStructMapper.map(message));
    }
}
