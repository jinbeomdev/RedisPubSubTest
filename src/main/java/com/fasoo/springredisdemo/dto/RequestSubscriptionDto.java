package com.fasoo.springredisdemo.dto;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestSubscriptionDto {

  private String userId;
  private Long postId;

  public RequestSubscriptionDto(String userId, Long postId) {
    this.userId = userId;
    this.postId = postId;
  }

  public SubscriptionDomain toSubscriptionDomain() {
    return SubscriptionDomain.builder()
      .userId(userId)
      .postId(postId)
      .build();
  }
}
