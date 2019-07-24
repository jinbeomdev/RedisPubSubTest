package com.fasoo.springredisdemo.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@RedisHash("Subscription")
public class Subscription implements Serializable {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;

  private int postId;

  @Builder
  public Subscription(String userId, int postId) {
    this.userId = userId;
    this.postId = postId;
  }
}
