package org.test.microservice.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class GetMessageUseCaseImpl implements GetMessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageMapStructMapper messageMapStructMapper;

    @Override
    public Page<Message> getAll(Pageable pageable) {
        Page<MessageEntity> messageEntities = messageRepository.findAll(pageable);
        return new PageImpl<>(messageMapStructMapper.map(messageEntities.getContent()), pageable, messageEntities.getTotalElements());
    }

    @Override
    public List<Message> findAllByType(MessageType type) {
        List<MessageEntity> messageEntities = messageRepository.findAllByType(type);
        final List<Message> cachedMessages = messageMapStructMapper.map(messageEntities);
        return cachedMessages;
    }

    @Override
    public Message getById(long id) {
        Optional<MessageEntity> messageEntityOpt = messageRepository.findAllById(id);
        return messageEntityOpt.map(messageMapStructMapper::map).orElse(null);
    }
}
