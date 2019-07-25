package com.fasoo.springredisdemo.repository;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRedisRepository extends CrudRepository<SubscriptionDomain, Long> {
}
