package com.mizegret.mps.mps_api.proxy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ProxyRoutePoolTest {

  @Test
  void missingResource_leadsToEmptyPool() throws IOException {
    ProxyRoutePool pool = new ProxyRoutePool();
    assertTrue(pool.isEmpty());
  }
}
