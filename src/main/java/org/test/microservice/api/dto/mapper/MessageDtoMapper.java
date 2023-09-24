package org.test.microservice.api.dto.mapper;

import org.mapstruct.Mapper;
import org.test.microservice.api.dto.MessageDto;
import org.test.microservice.usecase.model.Message;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageDtoMapper {

  MessageDto map(Message message);

  List<MessageDto> map(List<Message> message);
}
