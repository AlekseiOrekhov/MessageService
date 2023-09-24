package org.test.microservice.usecase.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.test.microservice.database.entity.MessageEntity;
import org.test.microservice.usecase.model.Message;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapStructMapper {

    Message map(MessageEntity messageEntity);

    List<Message> map(List<MessageEntity> messageEntity);

    // Реализовано чтобы при добавлении id мы все равно сохранили сообщение, но без этого id
    @Mapping(target = "id", ignore = true)
    MessageEntity map(Message message);
}
