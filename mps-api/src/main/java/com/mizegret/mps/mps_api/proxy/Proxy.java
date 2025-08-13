package com.mizegret.mps.mps_api.proxy;

import lombok.NonNull;
import lombok.Value;

@Value
public class Proxy {
  @NonNull String host;

  int port;

  @NonNull String userName;

  @NonNull String password;
}
