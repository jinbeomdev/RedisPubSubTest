package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.redis.EventMessage;
import com.fasoo.springredisdemo.redis.MessagePublisher;
import com.fasoo.springredisdemo.redis.RedisMessageSubscriber;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
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
				.postId(1L)
				.action("1번 게시글 수정했다!!!")
				.build();

		messagePublisher.publish(eventMessage);

		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);

		EventMessage receivedEventMessage = redisMessageSubscriber.eventMessageList.get(0);
		assertThat(receivedEventMessage.getUserId()).isEqualTo(eventMessage.getUserId());
		assertThat(receivedEventMessage.getPostId()).isEqualTo(eventMessage.getPostId());
		assertThat(receivedEventMessage.getAction()).isEqualTo(eventMessage.getAction());
	}

	@Test
	public void 게시판에_변동이_생기면_구독자_에게_알려주기() throws InterruptedException {
		SubscriptionDomain subscriptionDomain;
		SubscriptionDomain subscriptionDomain2;
		SubscriptionDomain subscriptionDomain3;

		subscriptionDomain = new SubscriptionDomain("jinbeom", 1L);
		subscriptionRedisRepository.save(subscriptionDomain);
		subscriptionDomain2 = new SubscriptionDomain("sangwon", 2L);
		subscriptionRedisRepository.save(subscriptionDomain2);
		subscriptionDomain3 = new SubscriptionDomain("gyeongjae", 1L);
		subscriptionRedisRepository.save(subscriptionDomain3);

		EventMessage eventMessage =
			EventMessage.builder()
				.userId("jinbeom")
				.postId(1L)
				.action("1번 게시판을 수정했습니다.")
				.build();

		messagePublisher.publish(eventMessage);

		redisMessageSubscriber.countDownLatch.await(2000L, TimeUnit.MILLISECONDS);

		EventMessage receivedEventMessage = redisMessageSubscriber.eventMessageList.get(0);
		assertThat(receivedEventMessage.getUserId()).isEqualTo(eventMessage.getUserId());
		assertThat(receivedEventMessage.getPostId()).isEqualTo(eventMessage.getPostId());
		assertThat(receivedEventMessage.getAction()).isEqualTo(eventMessage.getAction());

		List<SubscriptionDomain> subscribers =
			subscriptionRedisRepository.findByPostId(receivedEventMessage.getPostId());

		assertThat(subscribers.size()).isEqualTo(2);

		for(SubscriptionDomain subscriber : subscribers) {
			log.info(subscriber.getUserId() + "에게 알림 보내는 중.");
			log.info("<=== " + receivedEventMessage.getUserId() + "가 " + receivedEventMessage.getAction() + "햇습니다");
		}
	}
}
