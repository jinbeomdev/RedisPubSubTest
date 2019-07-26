package com.fasoo.springredisdemo;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringRedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisDemoApplication.class, args);
	}
}
