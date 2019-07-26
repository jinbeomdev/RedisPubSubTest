package com.fasoo.springredisdemo.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor //for jackson lib
@Getter
public class EventMessage {

  String userId;
  Long postId;
  String action;

  @Builder
  public EventMessage(String userId, Long postId, String action) {
    this.userId = userId;
    this.postId = postId;
    this.action = action;
  }

  @Override
  public String toString() {
    return "userId : " + userId + " postId : " + postId + " action : " + action;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof EventMessage) return false;

    EventMessage rhs = (EventMessage)obj;

    if(!userId.equals(rhs.userId)) return false;
    if(postId != rhs.postId) return false;
    if(!action.equals(action)) return false;

    return true;
  }
}
