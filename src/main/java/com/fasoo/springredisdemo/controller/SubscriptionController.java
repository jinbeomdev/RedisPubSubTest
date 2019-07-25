package com.fasoo.springredisdemo.controller;

import com.fasoo.springredisdemo.dto.RequestSubscriptionDto;
import com.fasoo.springredisdemo.dto.ResponseSubScriptionDto;
import com.fasoo.springredisdemo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

  @Autowired
  SubscriptionService subscriptionService;

  @PostMapping("/{postId}")
  public ResponseSubScriptionDto subscribe (@PathVariable (name = "postId") Long postId,
                                            @RequestBody RequestSubscriptionDto requestSubscriptionDto) {

    return subscriptionService.subscribe(requestSubscriptionDto).toResponseDto();
  }

  @DeleteMapping("/{id}")
  public void unsubscribe (@PathVariable (name = "id") Long id) {

    subscriptionService.unsubscribe(id);
  }
}
