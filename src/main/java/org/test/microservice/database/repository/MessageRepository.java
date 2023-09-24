package org.test.microservice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.microservice.database.entity.MessageEntity;
import org.test.microservice.en.MessageType;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    Optional<MessageEntity> findAllById(long id);

    List<MessageEntity> findAllByType(MessageType type);

    Integer countMessageEntitiesByType(MessageType type);
}
