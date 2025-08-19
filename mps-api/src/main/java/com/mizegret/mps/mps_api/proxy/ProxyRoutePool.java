package com.mizegret.mps.mps_api.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProxyRoutePool {

  private final List<ProxyRoute> proxyRoutes;
  private final AtomicInteger idx = new AtomicInteger(0);

  public ProxyRoutePool() throws IOException {
    List<Proxy> proxies = Collections.emptyList();
    final InputStream is = getClass().getResourceAsStream("/proxy-list.txt");
    if (is == null) {
      log.warn("proxy-list.txt not found; no proxies will be configured");
    } else {
      try (final BufferedReader br =
          new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
        proxies =
            br.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .map(line -> line.split(":", 4))
                .filter(parts -> parts.length == 4)
                .map(l -> new Proxy(l[0], Integer.parseInt(l[1]), l[2], l[3]))
                .toList();
      }
    }
    this.proxyRoutes =
        proxies.stream()
            .map(
                p ->
                    new ProxyRoute(
                        HttpClient.newBuilder()
                            .connectTimeout(Duration.ofSeconds(10))
                            .proxy(
                                ProxySelector.of(new InetSocketAddress(p.getHost(), p.getPort())))
                            .authenticator(
                                new Authenticator() {
                                  @Override
                                  protected PasswordAuthentication getPasswordAuthentication() {
                                    if (getRequestorType() == RequestorType.PROXY) {
                                      return new PasswordAuthentication(
                                          p.getUserName(), p.getPassword().toCharArray());
                                    }
                                    return null;
                                  }
                                })
                            .build(),
                        p))
            .toList();
  }

  public boolean isEmpty() {
    return proxyRoutes.isEmpty();
  }

  public ProxyRoute next() {
    if (proxyRoutes.isEmpty()) {
      throw new IllegalStateException("Proxy client pool is empty");
    }
    int i = Math.floorMod(idx.getAndIncrement(), proxyRoutes.size());
    return proxyRoutes.get(i);
  }
}
