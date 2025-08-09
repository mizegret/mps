package com.mizegret.mps.mps_api.service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;

import org.springframework.lang.NonNull;

public interface BicCameraHttpService{

    @NonNull
    HttpRequest buildGetHttpRequest(@NonNull String url, @NonNull LinkedHashMap<String, String> headersOrdered);

    @NonNull
    LinkedHashMap<String, String> buildBicCameraHeaders();
    
    @NonNull
    HttpResponse<byte[]> sendBytes(@NonNull HttpRequest request) throws Exception;
}
