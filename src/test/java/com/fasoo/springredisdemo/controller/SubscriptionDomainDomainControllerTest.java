package com.fasoo.springredisdemo.controller;

import com.fasoo.springredisdemo.domain.SubscriptionDomain;
import com.fasoo.springredisdemo.dto.RequestSubscriptionDto;
import com.fasoo.springredisdemo.dto.ResponseSubScriptionDto;
import com.fasoo.springredisdemo.repository.SubscriptionRedisRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SubscriptionDomainDomainControllerTest {

  @Autowired
  SubscriptionRedisRepository subscriptionRedisRepository;

  private final String URL = "http://localhost:8080/subscription/{postId}";

  private RestTemplate restTemplate;
  private HttpHeaders httpHeaders;

  @Before
  public void setUp() {
    restTemplate = new RestTemplate();
    httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  }

  @After
  public void tearDown() {
    subscriptionRedisRepository.deleteAll();
  }


  @Test
  public void 구독하기() {
    RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto("jinbeom", 0L);
    HttpEntity<RequestSubscriptionDto> request = new HttpEntity<>(requestSubscriptionDto, httpHeaders);

    ResponseSubScriptionDto response =
      restTemplate.postForObject(URL, request, ResponseSubScriptionDto.class, 0);

    SubscriptionDomain savedSubscription =
      subscriptionRedisRepository.findById(response.getId()).get();

    assertThat(savedSubscription.getUserId()).isEqualTo(requestSubscriptionDto.getUserId());
    assertThat(savedSubscription.getPostId()).isEqualTo(requestSubscriptionDto.getPostId());
  }

  @Test (expected = NoSuchElementException.class)
  public void 구독취소하기() {
    RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto("jinbeom", 0L);
    HttpEntity<RequestSubscriptionDto> request = new HttpEntity<>(requestSubscriptionDto, httpHeaders);

    ResponseSubScriptionDto response =
      restTemplate.postForObject(URL, request, ResponseSubScriptionDto.class, 0);

    SubscriptionDomain savedSubscription =
      subscriptionRedisRepository.findById(response.getId()).get();

    assertThat(savedSubscription.getUserId()).isEqualTo(requestSubscriptionDto.getUserId());
    assertThat(savedSubscription.getPostId()).isEqualTo(requestSubscriptionDto.getPostId());

    restTemplate.delete(URL, savedSubscription.getId());

    subscriptionRedisRepository.findById(savedSubscription.getId()).get();
  }
}
