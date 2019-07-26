package com.fasoo.springredisdemo.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class RedisMessagePublisher implements MessagePublisher {

  private RedisTemplate<String, ?> redisTemplate;
  private ChannelTopic topic;

  public RedisMessagePublisher(
    RedisTemplate<String, ?> redisTemplate, ChannelTopic topic) {
    this.redisTemplate = redisTemplate;
    this.topic = topic;
  }

  public void publish(Object message) {
    redisTemplate.convertAndSend(topic.getTopic(), message);
  }
}