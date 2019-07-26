package com.fasoo.springredisdemo.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RedisMessageSubscriber implements MessageListener {

  public static List<EventMessage> eventMessageList = new ArrayList<>();
  public static CountDownLatch countDownLatch = new CountDownLatch(1);

  private RedisTemplate<String, ?> redisTemplate;

  public RedisMessageSubscriber(RedisTemplate<String, ?> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void onMessage(Message message, byte[] pattern) {
    byte[] body = message.getBody();
    EventMessage receivedMsg = (EventMessage)redisTemplate.getValueSerializer().deserialize(body);
    eventMessageList.add(receivedMsg);
    countDownLatch.countDown();
  }
}