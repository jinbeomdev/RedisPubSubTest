package com.fasoo.springredisdemo.redis;

public interface MessagePublisher<V> {
  void publish(V message);
}
