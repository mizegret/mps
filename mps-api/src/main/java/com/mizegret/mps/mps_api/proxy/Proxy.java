package com.mizegret.mps.mps_api.proxy;

import org.springframework.lang.NonNull;

import lombok.Value;

@Value
public class Proxy{
    @NonNull
    String host;

    @NonNull
    int port;

    @NonNull
    String userName;

    @NonNull
    String password;
}
