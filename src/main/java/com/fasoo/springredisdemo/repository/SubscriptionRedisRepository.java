package com.fasoo.springredisdemo.repository;

import com.fasoo.springredisdemo.domain.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRedisRepository extends CrudRepository<Subscription, Long> {
}
