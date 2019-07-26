package com.fasoo.springredisdemo.repository;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionRedisRepositoryTest {

  @Autowired
  SubscriptionRedisRepository repository;

  @After
  public void 지우기() {
    repository.deleteAll();
  }

  @Test
  public void postId로_찾기() {
    SubscriptionDomain subscriptionDomain;
    SubscriptionDomain subscriptionDomain2;
    SubscriptionDomain subscriptionDomain3;

    subscriptionDomain = new SubscriptionDomain("jinbeom", 1L);
    repository.save(subscriptionDomain);
    subscriptionDomain2 = new SubscriptionDomain("hello", 1L);
    repository.save(subscriptionDomain2);
    subscriptionDomain3 = new SubscriptionDomain("plz", 1L);
    repository.save(subscriptionDomain3);

    List<SubscriptionDomain> found = repository.findByPostId(1L);

    for(SubscriptionDomain domain : found) {
      System.out.println(domain.getId() + " " + domain.getUserId() + " " + domain.getPostId());
    }
  }
}
