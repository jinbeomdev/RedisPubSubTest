package com.fasoo.springredisdemo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseSubScriptionDto {

  private Long id;
  private String userId;
  private Long postId;

  @Builder
  public ResponseSubScriptionDto(Long id, String userId, Long postId) {
    this.id = id;
    this.userId = userId;
    this.postId = postId;
  }
}
