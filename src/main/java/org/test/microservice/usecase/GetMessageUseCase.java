package org.test.microservice.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.test.microservice.en.MessageType;
import org.test.microservice.usecase.model.Message;

import java.util.List;

public interface GetMessageUseCase {

    Page<Message> getAll(Pageable pageable);

    List<Message> findAllByType(MessageType type);

    Message getById(long id);
}
