package com.fasoo.springredisdemo.service;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.dto.RequestSubscriptionDto;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

  @Autowired
  SubscriptionRedisRepository subscriptionRedisRepository;

  public SubscriptionDomain subscribe(RequestSubscriptionDto requestSubscriptionDto) {
    return subscriptionRedisRepository.save(requestSubscriptionDto.toSubscriptionDomain());
  }

  public void unsubscribe(Long id) {
    SubscriptionDomain subscriptionDomain =
      subscriptionRedisRepository.findById(id).get();

    subscriptionRedisRepository.delete(subscriptionDomain);
  }
}