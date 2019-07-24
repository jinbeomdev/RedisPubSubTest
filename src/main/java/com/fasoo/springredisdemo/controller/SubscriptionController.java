package com.fasoo.springredisdemo.controller;

import com.fasoo.springredisdemo.domain.Subscription;
import com.fasoo.springredisdemo.dto.SubscriptionDto;
import com.fasoo.springredisdemo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

  @Autowired
  SubscriptionService subscriptionService;

  @PostMapping("/{postId}")
  public Subscription subscribe (@PathVariable (name = "postId") Long postId,
                                 SubscriptionDto subscriptionDto) {

    return subscriptionService.subscribe(subscriptionDto);
  }

  @DeleteMapping("/{postId}")
  public void unsubscribe (@PathVariable (name = "postId") Long postId) {

    subscriptionService.unsubscribe(postId);
  }
}
