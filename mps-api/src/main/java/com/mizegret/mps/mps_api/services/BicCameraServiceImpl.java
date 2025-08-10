package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.BicCameraRequest;
import com.mizegret.mps.mps_api.dtos.BicCameraResponse;
import com.mizegret.mps.mps_shared.enums.ExtractFailureType;
import com.mizegret.mps.mps_shared.exception.BlockedException;
import com.mizegret.mps.mps_shared.exception.ExtractFailureException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BicCameraServiceImpl implements BicCameraService {

  private static final Path DUMP_HTML_PATH = Path.of("html", "last.html");

  private final HttpService httpService;

  @Override
  @NonNull
  public BicCameraResponse scrape(@NonNull BicCameraRequest request) throws Exception {
    // Fetch and decode HTML correctly (handles Shift_JIS via header or meta)
    final Document document = fetchDocument(request.getProductUrl());
    return parse(document, request.getProductUrl());
  }

  @NonNull
  private BicCameraResponse parse(@NonNull Document document, @NonNull String productUrl)
      throws ExtractFailureException {
    final String productName = extractProductName(document, productUrl);
    final int price = extractPrice(document, productUrl);

    return BicCameraResponse.builder().productName(productName).price(price).build();
  }

  @NonNull
  private int extractPrice(@NonNull Document document, @NonNull String productUrl)
      throws ExtractFailureException {
    final String fieldName = "price";
    final String selector = "strong[itemprop=price]";
    final Elements elements = document.select(selector);

    if (elements.isEmpty()) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.ELEMENT_NOT_FOUND);
    } else if (elements.size() > 1) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.MULTIPLE_ELEMENTS);
    }

    final Element element = elements.first();
    final String attributeKey = "content";

    if (!element.hasAttr(attributeKey)) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.ATTRIBUTE_KEY_MISSING);
    }

    final String priceStr = element.attr(attributeKey);

    if (priceStr.isBlank()) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.ATTRIBUTE_VALUE_BLANK);
    }

    try {
      return Integer.parseInt(priceStr);
    } catch (NumberFormatException e) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.NUMBER_PARSE_ERROR);
    }
  }

  @NonNull
  private String extractProductName(@NonNull Document document, @NonNull String productUrl)
      throws ExtractFailureException {
    final String fieldName = "productName";
    final String selector = "#PROD-CURRENT-NAME";
    final Elements elements = document.select(selector);

    if (elements.isEmpty()) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.ELEMENT_NOT_FOUND);
    }

    final Element element = elements.first();
    final String productName = element.ownText();

    if (productName.isBlank()) {
      throw new ExtractFailureException(
          productUrl, fieldName, selector, ExtractFailureType.TEXT_BLANK);
    }
    return productName;
  }

  @NonNull
  private Document fetchDocument(@NonNull String url) throws Exception {
    final LinkedHashMap<String, String> headers = buildBicCameraHeaders();
    final HttpRequest httpRequest = httpService.buildGetHttpRequest(url, headers);

    log.info("[fetchDoc] GET {} headers={}", url, sanitizeHeaders(headers));

    final HttpResponse<byte[]> response = httpService.sendBytes(httpRequest);
    final int statusCode = response.statusCode();
    final String enc = response.headers().firstValue(HttpHeaders.CONTENT_ENCODING).orElse("");
    final String contentType = response.headers().firstValue(HttpHeaders.CONTENT_TYPE).orElse("");

    log.info(
        "[fetchDoc] <{}> status={} content-type={} content-encoding={}",
        url,
        statusCode,
        contentType,
        enc);

    final byte[] body = response.body();
    final byte[] decodedBytes =
        switch (enc.toLowerCase(Locale.ROOT)) {
          case "gzip" -> new GZIPInputStream(new ByteArrayInputStream(body)).readAllBytes();
          case "deflate" ->
              new InflaterInputStream(new ByteArrayInputStream(body), new Inflater(true))
                  .readAllBytes();
          default -> body;
        };
    final Charset charset =
        Optional.ofNullable(MediaType.parseMediaType(contentType).getCharset())
            .orElse(StandardCharsets.UTF_8);
    final String html = new String(decodedBytes, charset); // Decode using charset
    dumpHtml(html, charset); // Dump HTML for debugging

    if (statusCode == HttpStatus.FORBIDDEN.value()) {
      throw new BlockedException("Upstream site blocked the request. " + url);
    }
    // Let Jsoup handle charset; if charset is null, it will sniff meta/bom
    return Jsoup.parse(html);
  }

  @NonNull
  private LinkedHashMap<String, String> buildBicCameraHeaders() {
    LinkedHashMap<String, String> headers = new LinkedHashMap<>();

    // Preserve order to mimic a real browser request as closely as possible
    headers.put("Sec-Ch-Ua", "Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138");
    headers.put("Sec-Ch-Ua-Mobile", "?0");
    headers.put("Sec-Ch-Ua-Platform", "Windows");
    headers.put("Accept-Language", "en-US,en;q=0.9");
    headers.put("Upgrade-Insecure-Requests", "1");
    headers.put(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/138.0.0.0 Safari/537.36");
    headers.put(
        "Accept",
        "text/html,application/xhtml+xml,application/xml;q=0.9,"
            + "image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
    headers.put("Sec-Fetch-Site", "none");
    headers.put("Sec-Fetch-Mode", "navigate");
    headers.put("Sec-Fetch-User", "?1");
    headers.put("Sec-Fetch-Dest", "document");
    headers.put("Accept-Encoding", "gzip, deflate, br");
    headers.put("Priority", "u=0, i");

    return headers;
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

  private void dumpHtml(@NonNull String html, @NonNull Charset charset) throws IOException {
    Files.createDirectories(DUMP_HTML_PATH.getParent());
    Files.writeString(
        DUMP_HTML_PATH,
        html,
        charset,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.WRITE);
    log.info("HTML dumped -> {}", DUMP_HTML_PATH.toAbsolutePath());
  }
}
