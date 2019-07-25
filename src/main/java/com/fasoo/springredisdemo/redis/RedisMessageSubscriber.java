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
    System.out.println("Message received : " + message.toString());
    String msg = message.toString().replaceAll("[^0-9]", "");
    messageList.add(msg);
    System.out.println("게시글(" + msg + ")에서 이벤트 발생");
    countDownLatch.countDown();
  }
}