package org.test.microservice.usecase;

import org.jetbrains.annotations.NotNull;
import org.test.microservice.usecase.model.Message;

import java.util.List;

public interface SaveMessageUseCase {

    /**
     * Метод сохраняет и отдает те сущности, которые он сохранил
     *
     * @param message - список сообщений, которые требуется провалидировать и сохранить
     * @return - список сообщений, которые прошли валидацию и сохранились
     */
    List<Message> saveAll(@NotNull List<Message> message);
}
