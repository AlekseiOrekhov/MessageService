package org.test.microservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.microservice.api.dto.MessageDto;
import org.test.microservice.api.presenter.MessagePresenter;
import org.test.microservice.en.MessageType;
import org.test.microservice.rabbit.RabbitMQProducer;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private RabbitMQProducer producer;
    @Autowired
    private MessagePresenter messagePresenter;

    @GetMapping
    @NotNull
    public Page<MessageDto> getAll(Pageable pageable) {
        return messagePresenter.getAll(pageable);
    }

    @GetMapping("/{id}")
    @NotNull
    public MessageDto getById(@PathVariable long id) {
        return messagePresenter.getById(id);
    }

    @GetMapping(params = "type")
    @NotNull
    @Cacheable(cacheNames="messageByType")
    public List<MessageDto> getByType(@RequestParam MessageType type) {
        return messagePresenter.getByType(type);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }

}
