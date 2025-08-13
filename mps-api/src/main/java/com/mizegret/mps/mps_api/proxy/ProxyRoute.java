package com.mizegret.mps.mps_api.proxy;

import java.net.http.HttpClient;
import lombok.NonNull;
import lombok.Value;

@Value
public class ProxyRoute {
  @NonNull private final HttpClient httpClient;
  @NonNull private final Proxy proxy;
}
