package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

  @Autowired
  SubscriptionRedisRepository subscriptionRedisRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("jinbeom")
      .postId(1L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("sangwon")
      .postId(1L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("gyeongjae")
      .postId(1L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("jinbeom")
      .postId(2L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("sangwon")
      .postId(2L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("jinbeom")
      .postId(3L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("sangwon")
      .postId(4L)
      .build());

    subscriptionRedisRepository.save(SubscriptionDomain.builder()
      .userId("gyeongjae")
      .postId(5L)
      .build());
  }
}
