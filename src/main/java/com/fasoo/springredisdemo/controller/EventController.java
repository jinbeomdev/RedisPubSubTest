package com.fasoo.springredisdemo.controller;

import com.fasoo.springredisdemo.redis.EventMessage;
import com.fasoo.springredisdemo.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/event")
public class EventController {

  @Autowired
  MessagePublisher messagePublisher;

  @PostMapping("")
  public void pushMessage(@RequestBody EventMessage eventMessage) {
    messagePublisher.publish(eventMessage);
  }
}
