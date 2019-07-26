package com.fasoo.springredisdemo.redis;

import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisPubSubConfig {

  @Autowired
  SubscriptionRedisRepository subscriptionRedisRepository;

  @Autowired
  RedisConnectionFactory redisConnectionFactory;

  @Autowired
  private RedisTemplate<String, ?> redisTemplate;

  @Bean
  public MessagePublisher messagePublisher() {
    return new RedisMessagePublisher(redisTemplate, topic());
  }

  @Bean
  public RedisMessageSubscriber redisMessageSubscriber() {
    return new RedisMessageSubscriber(redisTemplate, subscriptionRedisRepository);
  }


  @Bean
  MessageListenerAdapter messageListener() {
    MessageListenerAdapter messageListenerAdapter =
      new MessageListenerAdapter(redisMessageSubscriber());
    messageListenerAdapter.setSerializer(new GenericJackson2JsonRedisSerializer());
    return messageListenerAdapter;
  }

  @Bean
  RedisMessageListenerContainer redisContainer() {
    RedisMessageListenerContainer container
      = new RedisMessageListenerContainer();
    container.setConnectionFactory(redisConnectionFactory);
    container.addMessageListener(messageListener(), topic());
    return container;
  }

  @Bean
  ChannelTopic topic() {
    return new ChannelTopic("messageQueue");
  }
}
