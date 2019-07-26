package com.fasoo.springredisdemo.redis;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class RedisMessageSubscriber implements MessageListener {

  public static List<EventMessage> eventMessageList = new ArrayList<>();
  public static CountDownLatch countDownLatch = new CountDownLatch(1);

  private SubscriptionRedisRepository repository;
  private RedisTemplate<String, ?> redisTemplate;

  public RedisMessageSubscriber(RedisTemplate<String, ?> redisTemplate,
                                SubscriptionRedisRepository repository) {
    this.redisTemplate = redisTemplate;
    this.repository = repository;
  }

  public void onMessage(Message message, byte[] pattern) {
    byte[] body = message.getBody();
    EventMessage receivedMsg = (EventMessage)redisTemplate.getValueSerializer().deserialize(body);
    eventMessageList.add(receivedMsg);
    countDownLatch.countDown();

    List<SubscriptionDomain> subscribers =
      repository.findByPostId(receivedMsg.getPostId());

    for(SubscriptionDomain subscriber : subscribers) {
      log.info(subscriber.getUserId() + "에게 알림 보내는 중.");
      log.info("<=== " + receivedMsg.getUserId() + "가 " + receivedMsg.getAction() + "햇습니다");
    }
  }
}