package com.fasoo.springredisdemo.service;

import com.fasoo.springredisdemo.domain.Subscription;
import com.fasoo.springredisdemo.dto.SubscriptionDto;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

  SubscriptionRedisRepository subscriptionRedisRepository;

  public Subscription subscribe(SubscriptionDto subscriptionDto) {
    return subscriptionRedisRepository.save(subscriptionDto.getSubscription());
  }

  public void unsubscribe(Long subscriptionId) {
    subscriptionRedisRepository.deleteById(subscriptionId);
  }
}
