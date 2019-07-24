package com.fasoo.springredisdemo.redis;

public interface MessagePublisher {
  void publish(String message);
}
