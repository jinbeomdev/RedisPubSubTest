package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.Subscription;
import com.fasoo.springredisdemo.dto.SubscriptionDto;
import com.fasoo.springredisdemo.redis.MessagePublisher;
import com.fasoo.springredisdemo.redis.RedisMessageSubscriber;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedisDemoApplicationTests {

	@Autowired
	private SubscriptionRedisRepository subscriptionRedisRepository;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private RedisMessageSubscriber redisMessageSubscriber;

	private CountDownLatch lock = new CountDownLatch(1);

	@After
	public void tearDown() throws Exception {
		subscriptionRedisRepository.deleteAll();
	}

	@Test
	public void 구독() {
		String userId = "jinbeom";
		int postId = new Random().nextInt();
		SubscriptionDto subscriptionDto = new SubscriptionDto(userId, postId);

		Subscription subscription =
			subscriptionRedisRepository.save(subscriptionDto.getSubscription());

		Subscription savedSubscription = subscriptionRedisRepository.findById(subscription.getId()).get();
		assertThat(savedSubscription.getUserId()).isEqualTo(userId);
		assertThat(savedSubscription.getPostId()).isEqualTo(postId);
	}

	@Test (expected = NoSuchElementException.class)
	public void 구독_취소() {
		String userId = "jinbeom";
		int postId = new Random().nextInt();
		SubscriptionDto subscriptionDto = new SubscriptionDto(userId, postId);

		Subscription subscription =
			subscriptionRedisRepository.save(subscriptionDto.getSubscription());

		Subscription savedSubscription =
			subscriptionRedisRepository.findById(subscription.getId()).get();

		assertThat(savedSubscription.getUserId()).isEqualTo(userId);
		assertThat(savedSubscription.getPostId()).isEqualTo(postId);

		subscriptionRedisRepository.deleteById(subscription.getId());

		try {
			subscriptionRedisRepository.findById(subscription.getId()).get();
		} catch (NoSuchElementException ex) {
			throw ex;
		}

		assertTrue(false);
	}

	@Test
	public void 메시지_보내기_받기() throws InterruptedException {
		String SentMessage = "Message " + UUID.randomUUID();

		messagePublisher.publish(SentMessage);
		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
		String receivedMessage = redisMessageSubscriber.messageList.get(0);

		assertThat(receivedMessage).isEqualTo(SentMessage);
	}
}
