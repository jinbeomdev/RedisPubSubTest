package com.fasoo.springredisdemo.controller;

import com.fasoo.springredisdemo.redis.EventMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventController {

  @Test
  public void RestAPI으로_이벤트_보내기() {
    EventMessage message = EventMessage.builder()
      .userId("김진범")
      .postId(1L)
      .action("읽었음")
      .build();

    String url = "http://localhost:8080/event";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity httpEntity = new HttpEntity(message, headers);

    restTemplate.postForObject(url, message, String.class);
  }
}
