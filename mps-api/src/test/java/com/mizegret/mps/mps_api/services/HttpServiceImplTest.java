package com.mizegret.mps.mps_api.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import com.mizegret.mps.mps_api.proxy.ProxyRoutePool;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HttpServiceImplTest {

  @Mock private ProxyRoutePool proxyRoutePool;
  @Mock private HttpClient defaultHttpClient;
  @Mock private HttpRequest httpRequest;
  @Mock private HttpResponse<byte[]> httpResponse;

  @InjectMocks private HttpServiceImpl httpService;

  @BeforeEach
  void setUp() {
    // ensure inject mocks occurs
  }

  @Test
  void sendBytes_withoutProxies_usesDefaultClient() throws Exception {
    when(proxyRoutePool.isEmpty()).thenReturn(true);
    when(defaultHttpClient.send(eq(httpRequest), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    HttpResponse<byte[]> res = httpService.sendBytes(httpRequest);

    assertSame(httpResponse, res);
    verify(defaultHttpClient).send(eq(httpRequest), any(HttpResponse.BodyHandler.class));
    verify(proxyRoutePool, never()).next();
  }
}
