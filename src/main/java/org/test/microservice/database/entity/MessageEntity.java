package org.test.microservice.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.test.microservice.en.MessageType;

@Table(name = "message")
@Entity
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(name = "\"from\"")
    private String from;
    @Column(name = "\"to\"")
    private String to;
    private String text;
    @Enumerated(EnumType.STRING)
    @Column(name = "\"type\"")
    private MessageType type;
}
