package com.fasoo.springredisdemo.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RedisMessageSubscriber implements MessageListener {

  public static List<String> messageList = new ArrayList<>();
  public static CountDownLatch countDownLatch = new CountDownLatch(1);

  public void onMessage(Message message, byte[] pattern) {
    messageList.add(new String(message.getBody()));
    System.out.println("Message received : \n" +
      "message => " + new String(message.getBody()) + "\n" +
      "channel => " + new String(message.getChannel()) + "\n" +
      "pattern => " + new String(pattern));
    countDownLatch.countDown();
  }
}