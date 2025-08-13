package com.mizegret.mps.mps_core.config;

import java.net.http.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
  @Bean
  public HttpClient defaultHttpClient() {
    return HttpClient.newBuilder().build();
  }
}
