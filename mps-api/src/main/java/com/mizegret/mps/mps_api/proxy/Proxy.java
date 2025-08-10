package com.mizegret.mps.mps_api.proxy;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class Proxy {
  @NonNull String host;

  @NonNull int port;

  @NonNull String userName;

  @NonNull String password;
}
