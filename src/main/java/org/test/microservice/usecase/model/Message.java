package org.test.microservice.usecase.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.test.microservice.en.MessageType;

import java.io.Serializable;

@Data
public class Message implements Serializable {

  private long id;
  private String from;
  private String to;
  private String text;
  private MessageType type;
}
