package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.proxy.ProxyRoutePool;
import com.mizegret.mps.mps_api.proxy.ProxyRoute;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpServiceImpl implements HttpService{

    private final ProxyRoutePool proxyRoutePool;
    private final HttpClient defaultHttpClient;

    @NonNull
    public HttpRequest buildGetHttpRequest(@NonNull String url, @NonNull LinkedHashMap<String, String> headersOrdered){
        return buildHttpRequest(url, "GET", headersOrdered, BodyPublishers.noBody());
    }

    @NonNull
    @Override
    public HttpResponse<byte[]> sendBytes(@NonNull HttpRequest request) throws Exception{
        final ProxyRoute proxyRoute = proxyRoutePool.next();
        log.info("sending to {} via {}", request.uri(), proxyRoute.getProxy().getHost());
        return proxyRoute.getHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    @NonNull
    @Override
    public HttpResponse<byte[]> sendBytesByHost(@NonNull HttpRequest request) throws Exception{
        return defaultHttpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    @NonNull
    private HttpRequest buildHttpRequest(@NonNull String url, @NonNull String method,
            @NonNull LinkedHashMap<String, String> headersOrdered, @Nullable BodyPublisher body){
        final Builder builder = HttpRequest.newBuilder(URI.create(url));

        headersOrdered.forEach((k, v) -> {
            if(Objects.nonNull(k) && Objects.nonNull(v))
                builder.header(k, v);
        });

        final String m = method.toUpperCase(Locale.ROOT);
        final BodyPublisher bodyPublisher = Objects.nonNull(body) ? body : BodyPublishers.noBody();
        switch(m) {
            case "GET" -> builder.GET();
            case "POST" -> builder.method("POST", bodyPublisher);
            case "PUT" -> builder.method("PUT", bodyPublisher);
            case "PATCH" -> builder.method("PATCH", bodyPublisher);
            case "DELETE" -> builder.method("DELETE", bodyPublisher);
            default -> builder.method(m, bodyPublisher);
        }

        return builder.build();
    }
}
