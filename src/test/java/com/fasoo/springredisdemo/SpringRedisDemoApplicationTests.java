package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.Point;
import com.fasoo.springredisdemo.redis.RedisMessagePublisher;
import com.fasoo.springredisdemo.redis.RedisMessageSubscriber;
import com.fasoo.springredisdemo.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedisDemoApplicationTests {

	@Autowired
	private PointRedisRepository pointRedisRepository;

	@Autowired
	RedisMessagePublisher redisMessagePublisher;

	@Autowired
	RedisMessageSubscriber redisMessageSubscriber;

	@After
	public void tearDown() throws Exception {
		pointRedisRepository.deleteAll();
	}

	@Test
	public void 기본_등록_조회기능() {
		//given
		String id = "fasoo";
		LocalDateTime refreshTime = LocalDateTime.of(2019, 1, 16, 11, 15);
		Point point = Point.builder()
			.id(id)
			.amount(1000L)
			.refreshTime(refreshTime)
			.build();

		//when
		pointRedisRepository.save(point);

		//then
		Point savePoint = pointRedisRepository.findById(id).get();
		assertThat(savePoint.getAmount()).isEqualTo(1000L);
		assertThat(savePoint.getRefreshTime()).isEqualTo(refreshTime);
	}

	@Test
	public void 수정기능() {
		//given
		String id = "fasoo";
		LocalDateTime refreshTime = LocalDateTime.of(2019, 1, 16, 11, 15);
		pointRedisRepository.save(Point.builder()
		.id(id)
		.amount(1000L)
		.refreshTime(refreshTime)
		.build());

		//when
		Point savedPoint = pointRedisRepository.findById(id).get();
		savedPoint.refresh(2000L, LocalDateTime.of(2019, 7, 16, 11, 29));
		pointRedisRepository.save(savedPoint);

		//then
		Point refreshPoint = pointRedisRepository.findById(id).get();
		assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
	}

	@Test
	public void 메시지_보내기_받기() {
		String Sendedmessage = "Message " + UUID.randomUUID();

		redisMessagePublisher.publish(Sendedmessage);
		System.out.println("cnt " + redisMessageSubscriber.cnt);
		String receivedMessage = redisMessageSubscriber.messageList.get(0);

		assertThat(receivedMessage).isEqualTo(Sendedmessage);
	}
}
