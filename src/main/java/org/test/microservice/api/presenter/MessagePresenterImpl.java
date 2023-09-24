package org.test.microservice.api.presenter;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.test.microservice.api.dto.MessageDto;
import org.test.microservice.api.dto.mapper.MessageDtoMapper;
import org.test.microservice.en.MessageType;
import org.test.microservice.usecase.GetMessageUseCase;
import org.test.microservice.usecase.model.Message;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagePresenterImpl implements MessagePresenter {

    private final GetMessageUseCase getMessageUseCase;
    private final MessageDtoMapper messageDtoMapper;

    @Override
    @NotNull
    public Page<MessageDto> getAll(Pageable pageable) {
        Page<Message> messages = getMessageUseCase.getAll(pageable);
        return new PageImpl<>(messageDtoMapper.map(messages.getContent()), pageable, messages.getTotalElements());
    }

    @Override
    @NotNull
    public MessageDto getById(long id) {
        return messageDtoMapper.map(getMessageUseCase.getById(id));
    }

    @Override
    @NotNull
    public List<MessageDto> getByType(@NotNull MessageType type) {
        List<Message> messages = getMessageUseCase.findAllByType(type);
        return messageDtoMapper.map(messages);
    }
}
