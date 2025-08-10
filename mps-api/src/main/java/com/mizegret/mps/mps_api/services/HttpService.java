package com.mizegret.mps.mps_api.services;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;

import org.springframework.lang.NonNull;

public interface HttpService{

    @NonNull
    HttpRequest buildGetHttpRequest(@NonNull String url, @NonNull LinkedHashMap<String, String> headersOrdered);

    @NonNull
    HttpResponse<byte[]> sendBytes(@NonNull HttpRequest request) throws Exception;

    @NonNull
    HttpResponse<byte[]> sendBytesByHost(@NonNull HttpRequest request) throws Exception;
}
