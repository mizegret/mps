package com.mizegret.mps.mps_api.service;

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

@Service
public class BicCameraHttpServiceImpl implements BicCameraHttpService{

    @Override
    @NonNull
    public LinkedHashMap<String, String> buildBicCameraHeaders(){
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();

        // Preserve order to mimic a real browser request as closely as possible
        headers.put("Sec-Ch-Ua", "Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138");
        headers.put("Sec-Ch-Ua-Mobile", "?0");
        headers.put("Sec-Ch-Ua-Platform", "Windows");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/138.0.0.0 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,"
                + "image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("Sec-Fetch-Site", "none");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-User", "?1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Priority", "u=0, i");

        return headers;
    }
    
    @NonNull
    public HttpRequest buildGetHttpRequest(@NonNull String url, @NonNull LinkedHashMap<String, String> headersOrdered){
        return buildHttpRequest(url, "GET", headersOrdered, BodyPublishers.noBody());
    }

    @NonNull
    @Override
    public HttpResponse<byte[]> sendBytes(@NonNull HttpRequest request) throws Exception{
        final HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofByteArray());
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
