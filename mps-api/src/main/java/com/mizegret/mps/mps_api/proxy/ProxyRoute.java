package com.mizegret.mps.mps_api.proxy;

import java.net.http.HttpClient;
import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class ProxyRoute {
  @NonNull private final HttpClient httpClient;
  @NonNull private final Proxy proxy;
}
