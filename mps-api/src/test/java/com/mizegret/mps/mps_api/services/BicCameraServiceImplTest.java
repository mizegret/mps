package com.mizegret.mps.mps_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mizegret.mps.mps_api.dtos.BicCameraRequest;
import com.mizegret.mps.mps_api.dtos.BicCameraResponse;
import com.mizegret.mps.mps_shared.exception.BlockedException;
import com.mizegret.mps.mps_shared.exception.ExtractFailureException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class BicCameraServiceImplTest {

  @Mock private HttpService httpService;

  @Mock private HttpResponse<byte[]> httpResponse;

  @InjectMocks private BicCameraServiceImpl bicCameraService;

  private BicCameraRequest request;

  @BeforeEach
  void setUp() {
    request = new BicCameraRequest("https://www.biccamera.com/bc/item/12345/");
  }

  private String createDummyHtml(String productName, String price) {
    String h1 = (productName != null) ? String.format("<h1 id=\"PROD-CURRENT-NAME\">%s</h1>", productName) : "";
    String strong = (price != null) ? String.format("<strong itemprop=\"price\" content=\"%s\">¥%s</strong>", price, price) : "";

    return String.format(
        """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Test Product</title>
        </head>
        <body>
            %s
            <div class="bcs_price">
                %s
            </div>
        </body>
        </html>
        """,
        h1, strong);
  }

  @Test
  void scrape_success() throws Exception {
    // Arrange
    String productName = "Test Product Name";
    String price = "9980";
    String html = createDummyHtml(productName, price);
    byte[] responseBody = html.getBytes(StandardCharsets.UTF_8);

    when(httpService.buildGetHttpRequest(any(String.class), any()))
        .thenReturn(HttpRequest.newBuilder().uri(java.net.URI.create(request.getProductUrl())).build());
    when(httpService.sendBytes(any(HttpRequest.class))).thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
    when(httpResponse.body()).thenReturn(responseBody);
    when(httpResponse.headers()).thenReturn(httpResponseHeaders(""));

    // Act
    BicCameraResponse response = bicCameraService.scrape(request);

    // Assert
    assertNotNull(response);
    assertEquals(productName, response.getProductName());
    assertEquals(Integer.parseInt(price), response.getPrice());
  }

  @Test
  void scrape_blocked() throws Exception {
    // Arrange
    when(httpService.buildGetHttpRequest(any(String.class), any()))
        .thenReturn(HttpRequest.newBuilder().uri(java.net.URI.create(request.getProductUrl())).build());
    when(httpService.sendBytes(any(HttpRequest.class))).thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(HttpStatus.FORBIDDEN.value());
    when(httpResponse.body()).thenReturn(new byte[0]);
    when(httpResponse.headers()).thenReturn(httpResponseHeaders(""));


    // Act & Assert
    assertThrows(BlockedException.class, () -> bicCameraService.scrape(request));
  }

  @Test
  void scrape_extractProductName_notFound() throws Exception {
    // Arrange
    String html = createDummyHtml(null, "9980");
    byte[] responseBody = html.getBytes(StandardCharsets.UTF_8);

    when(httpService.buildGetHttpRequest(any(String.class), any()))
        .thenReturn(HttpRequest.newBuilder().uri(java.net.URI.create(request.getProductUrl())).build());
    when(httpService.sendBytes(any(HttpRequest.class))).thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
    when(httpResponse.body()).thenReturn(responseBody);
    when(httpResponse.headers()).thenReturn(httpResponseHeaders(""));

    // Act & Assert
    assertThrows(ExtractFailureException.class, () -> bicCameraService.scrape(request));
  }

  @Test
  void scrape_extractPrice_notFound() throws Exception {
    // Arrange
    String html = createDummyHtml("Test Product", null);
    byte[] responseBody = html.getBytes(StandardCharsets.UTF_8);

    when(httpService.buildGetHttpRequest(any(String.class), any()))
        .thenReturn(HttpRequest.newBuilder().uri(java.net.URI.create(request.getProductUrl())).build());
    when(httpService.sendBytes(any(HttpRequest.class))).thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
    when(httpResponse.body()).thenReturn(responseBody);
    when(httpResponse.headers()).thenReturn(httpResponseHeaders(""));

    // Act & Assert
    assertThrows(ExtractFailureException.class, () -> bicCameraService.scrape(request));
  }

  @Test
  void scrape_gzipEncoding() throws Exception {
    // Arrange
    String productName = "Gzipped Product";
    String price = "12345";
    String html = createDummyHtml(productName, price);
    
    // Gzip the HTML
    java.io.ByteArrayOutputStream byteStream = new java.io.ByteArrayOutputStream();
    try (java.util.zip.GZIPOutputStream gzipStream = new java.util.zip.GZIPOutputStream(byteStream)) {
        gzipStream.write(html.getBytes(StandardCharsets.UTF_8));
    }
    byte[] responseBody = byteStream.toByteArray();

    when(httpService.buildGetHttpRequest(any(String.class), any()))
        .thenReturn(HttpRequest.newBuilder().uri(java.net.URI.create(request.getProductUrl())).build());
    when(httpService.sendBytes(any(HttpRequest.class))).thenReturn(httpResponse);
    when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
    when(httpResponse.body()).thenReturn(responseBody);
    when(httpResponse.headers()).thenReturn(httpResponseHeaders("gzip"));

    // Act
    BicCameraResponse response = bicCameraService.scrape(request);

    // Assert
    assertNotNull(response);
    assertEquals(productName, response.getProductName());
    assertEquals(Integer.parseInt(price), response.getPrice());
  }

  private java.net.http.HttpHeaders httpResponseHeaders(String encoding) {
    return java.net.http.HttpHeaders.of(
        Map.of(
            "Content-Type",
            java.util.List.of("text/html; charset=UTF-8"),
            "Content-Encoding",
            java.util.List.of(encoding)),
        (a, b) -> true);
  }
}
