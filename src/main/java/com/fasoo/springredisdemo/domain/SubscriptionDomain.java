package com.fasoo.springredisdemo.domain;

import com.fasoo.springredisdemo.dto.ResponseSubScriptionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@RedisHash("SubscriptionDomain")
public class SubscriptionDomain implements Serializable {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @Indexed
  private String userId;

  @Indexed
  private Long postId;

  @Builder
  public SubscriptionDomain(String userId, Long postId) {
    this.userId = userId;
    this.postId = postId;
  }

  public ResponseSubScriptionDto toResponseDto() {
    return ResponseSubScriptionDto.builder()
      .id(id)
      .userId(userId)
      .postId(postId)
      .build();
  }
}
