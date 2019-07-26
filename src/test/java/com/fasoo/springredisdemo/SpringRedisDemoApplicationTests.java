package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.redis.EventMessage;
import com.fasoo.springredisdemo.redis.MessagePublisher;
import com.fasoo.springredisdemo.redis.RedisMessageSubscriber;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
		EventMessage eventMessage =
			EventMessage.builder()
				.userId("jinbeom")
				.postId(0L)
				.action("헤헤")
				.build();

		messagePublisher.publish(eventMessage);
		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
		EventMessage receivedEventMessage = redisMessageSubscriber.eventMessageList.get(0);
		assertThat(receivedEventMessage.getUserId()).isEqualTo(eventMessage.getUserId());
		assertThat(receivedEventMessage.getPostId()).isEqualTo(eventMessage.getPostId());
		assertThat(receivedEventMessage.getAction()).isEqualTo(eventMessage.getAction());
	}
}
