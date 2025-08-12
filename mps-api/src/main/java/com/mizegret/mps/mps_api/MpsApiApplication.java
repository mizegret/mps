package com.mizegret.mps.mps_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mizegret.mps")
public class MpsApiApplication {

  public static void main(String[] args) {
    System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
    System.setProperty("jdk.http.auth.proxying.disabledSchemes", "");
    SpringApplication.run(MpsApiApplication.class, args);
  }
}
