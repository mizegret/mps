package com.mizegret.mps.mps_api.service;

import com.mizegret.mps.mps_api.dto.BicCameraRequest;
import com.mizegret.mps.mps_api.dto.BicCameraResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BicCameraServiceImpl implements BicCameraService{

    private final BicCameraHttpService httpService;

    @Override
    @NonNull
    public BicCameraResponse scrape(@NonNull BicCameraRequest request){
        try{
            // Fetch and decode HTML correctly (handles Shift_JIS via header or meta)
            final Document doc = fetchDocumentWithBicCameraHeaders(request.getProductUrl());
            return new BicCameraResponse("productName", "productPrice");
        } catch (IOException e){
            throw new RuntimeException("Failed to parse the website: " + e.getMessage(), e);
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch the website: " + e.getMessage(), e);
        }
    }

    @NonNull
    private Document fetchDocumentWithBicCameraHeaders(@NonNull String url) throws Exception{
        final LinkedHashMap<String, String> headers = httpService.buildBicCameraHeaders();
        final HttpRequest httpRequest = httpService.buildGetHttpRequest(url, headers);

        log.info("[fetchDoc] GET {} headers={}", url, sanitizeHeaders(headers));
        
        final HttpResponse<byte[]> response = httpService.sendBytes(httpRequest);
        final int statusCode = response.statusCode();
        final String enc = response.headers().firstValue(HttpHeaders.CONTENT_ENCODING).orElse(null);
        final String contentType = response.headers().firstValue(HttpHeaders.CONTENT_TYPE).orElse(null);
        
        log.info("[fetchDoc] <{}> status={} content-type={} content-encoding={}", url, statusCode, contentType, enc);
        
        final byte[] body = response.body();
        final byte[] decodedBytes = switch(enc.toLowerCase(Locale.ROOT)) {
            case "gzip" -> new GZIPInputStream(new ByteArrayInputStream(body)).readAllBytes();
            case "deflate" ->
            new InflaterInputStream(new ByteArrayInputStream(body), new Inflater(true)).readAllBytes();
            default -> body;
        };
        final String charset = extractCharset(contentType);
        final String html = new String(decodedBytes, charset); // Decode using charset

        // Let Jsoup handle charset; if charset is null, it will sniff meta/bom
        return Jsoup.parse(html);
    }

    private String extractCharset(String contentType){
        if(contentType == null)
            return null;
        // e.g., "text/html; charset=Shift_JIS"
        String[] parts = contentType.split(";");
        for(String part : parts){
            String p = part.trim();
            int idx = p.toLowerCase(Locale.ROOT).indexOf("charset=");
            if(idx >= 0){
                String val = p.substring(idx + 8).trim();
                if(val.startsWith("\"") && val.endsWith("\"") && val.length() > 1){
                    val = val.substring(1, val.length() - 1);
                }
                return val;
            }
        }
        return null;
    }

    /** Authorization/Cookie などは伏せてログる */
    private Map<String, String> sanitizeHeaders(Map<String, String> headers) {
        Map<String, String> safe = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : headers.entrySet()) {
            String k = e.getKey();
            if (k.equalsIgnoreCase("Authorization")
             || k.equalsIgnoreCase("Cookie")
             || k.equalsIgnoreCase("Set-Cookie")) {
                safe.put(k, "***");
            } else {
                safe.put(k, e.getValue());
            }
        }
        return safe;
    }
}
