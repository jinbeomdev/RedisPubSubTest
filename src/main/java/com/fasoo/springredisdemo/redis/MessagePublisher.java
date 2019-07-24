package com.fasoo.springredisdemo.redis;

public interface MessagePublisher {
  void publish(Object object);
}
