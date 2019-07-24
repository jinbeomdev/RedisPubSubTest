package com.fasoo.springredisdemo.dto;

import com.fasoo.springredisdemo.domain.Subscription;

public class SubscriptionDto {

  private String userId;
  private int postId;

  public SubscriptionDto(String userId, int postId) {
    this.userId = userId;
    this.postId = postId;
  }

  public Subscription getSubscription() {
    return Subscription.builder()
      .userId(userId)
      .postId(postId)
      .build();
  }
}
