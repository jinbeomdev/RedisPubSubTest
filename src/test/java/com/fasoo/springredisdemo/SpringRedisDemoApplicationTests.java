package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.dto.RequestSubscriptionDto;
import com.fasoo.springredisdemo.redis.MessagePublisher;
import com.fasoo.springredisdemo.redis.RedisMessageSubscriber;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedisDemoApplicationTests {

	@Autowired
	private SubscriptionRedisRepository subscriptionRedisRepository;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private RedisMessageSubscriber redisMessageSubscriber;


	@Test
	public void 메시지_보내기_받기() throws InterruptedException {
		Long postId = (new Random()).nextLong();

		String message = postId.toString();

		messagePublisher.publish(message);
		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
		String receivedMessage = redisMessageSubscriber.messageList.get(0);
		assertThat(receivedMessage).isEqualTo(message);
	}

	@Test
	public void 객체를_메세지로_보내고_받기() throws InterruptedException {
		String userId = "jinbeom";
		Long postId = 123L;
		RequestSubscriptionDto request = new RequestSubscriptionDto(userId, postId);

		messagePublisher.publish(request);
		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
		String receivedMessage = redisMessageSubscriber.messageList.get(0);
		assertThat(receivedMessage).isEqualTo(request);
	}


	@Test
	public void 게시글의_구독자_리스트_가져오기() throws InterruptedException {
		Long postId = (new Random()).nextLong();

		SubscriptionDomain subscriptionDomain
			= subscriptionRedisRepository.save(SubscriptionDomain.builder()
				.userId("jinbeom")
				.postId(postId)
				.build());

		subscriptionRedisRepository.save(SubscriptionDomain.builder()
			.userId("jihye")
			.postId(postId)
			.build());

		String message = postId.toString();

		messagePublisher.publish(message);

		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);

		String receivedMessage = null;
		try {
			receivedMessage= redisMessageSubscriber.messageList.get(0);
		} catch (IndexOutOfBoundsException ex) {
			fail();
		}

		assertThat(receivedMessage).isNotNull();

		for(SubscriptionDomain subscriber : subscriptionRedisRepository.findAll()) {
			System.out.println(subscriber.getUserId() + "은 게시글(" + subscriber.getPostId() + ")을 구독중");
		}
	}
}
