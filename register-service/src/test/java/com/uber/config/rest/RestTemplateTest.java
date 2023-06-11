package com.uber.config.rest;

import com.uber.config.AppConfig;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestTemplateTest {
    @Test
    public void testRetryWithTreeFails() throws IOException {
        RestTemplate restTemplate = new AppConfig().restTemplate(3, 3500);

        try (MockWebServer mockWebServer = new MockWebServer()) {
            String expectedResponse = "expect that it works";
            mockWebServer.enqueue(new MockResponse().setResponseCode(429));
            mockWebServer.enqueue(new MockResponse().setResponseCode(502));
            mockWebServer.enqueue(new MockResponse().setResponseCode(429));
            mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                    .setBody(expectedResponse));

            mockWebServer.start();

            HttpUrl url = mockWebServer.url("/test");

            ResponseEntity<String> response = restTemplate.exchange(url.uri(), HttpMethod.GET, new HttpEntity<>(StringUtils.EMPTY, new HttpHeaders()), String.class);
            assertEquals(expectedResponse, response.getBody());

            mockWebServer.shutdown();
        }

    }
}
