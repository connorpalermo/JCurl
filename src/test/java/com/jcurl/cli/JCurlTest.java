package com.jcurl.cli;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JCurlTest {

    private static final int OKAY_STATUS = 200;
    private static final String GET_PATH = "get";
    private static final String DELETE_PATH = "delete";
    private static final String POST_PATH = "post";
    private static final String PUT_PATH = "put";
    private static final String PATCH = "patch";
    private String baseUrl;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        baseUrl = mockWebServer.url("").url().toString();
    }

    @Test
    public void testJCurlGet() {
        String response = """
                {
                  "args": {},\s
                  "headers": {
                    "Accept-Encoding": "gzip",\s
                    "Host": "www.httpbin.org",\s
                    "User-Agent": "okhttp/3.14.0",\s
                    "X-Amzn-Trace-Id": "Root=1-66ba2316-7bfc95f332e691c71fab4a37"
                  },\s
                  "origin": "100.8.48.199",\s
                  "url": "http://www.httpbin.org/get"
                }
        """;
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        JCurl jCurl = new JCurl();
        jCurl.setUrl(baseUrl+GET_PATH);
        jCurl.setMethod(GET_PATH);
        jCurl.setVerbose(true);
        jCurl.run();
    }

    @Test
    public void testJCurlDelete() {
        String response = """
                {
                  "args": {},\s
                  "data": "",\s
                  "files": {},\s
                  "form": {},\s
                  "headers": {
                    "Accept-Encoding": "gzip",\s
                    "Content-Length": "0",\s
                    "Host": "www.httpbin.org",\s
                    "User-Agent": "okhttp/3.14.0",\s
                    "X-Amzn-Trace-Id": "Root=1-66ba2487-0cee04fe020cce5b42aa4bcd"
                  },\s
                  "json": null,\s
                  "origin": "100.8.48.199",\s
                  "url": "http://www.httpbin.org/delete"
                }
                """;
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        JCurl jCurl = new JCurl();
        jCurl.setUrl(baseUrl+DELETE_PATH);
        jCurl.setMethod(DELETE_PATH);
        jCurl.setVerbose(true);
        jCurl.run();
    }

    @Test
    public void testJCurlPost() {
        String response = """
                {
                   "args": {},\s
                   "data": "{}",\s
                   "files": {},\s
                   "form": {},\s
                   "headers": {
                     "Accept-Encoding": "gzip",\s
                     "Content-Length": "2",\s
                     "Content-Type": "application/json; charset=utf-8",\s
                     "Host": "www.httpbin.org",\s
                     "User-Agent": "okhttp/3.14.0",\s
                     "X-Amzn-Trace-Id": "Root=1-66ba250a-31e0f8574415f3934eaaf6da"
                   },\s
                   "json": {},\s
                   "origin": "100.8.48.199",\s
                   "url": "http://www.httpbin.org/post"
                 }
                """;
        String testBody = """
                {
                   "Hello": "World"
                 }
                """;
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        JCurl jCurl = new JCurl();
        jCurl.setUrl(baseUrl+POST_PATH);
        jCurl.setMethod(POST_PATH);
        jCurl.setHeaders(new String[]{"Accept:application/json"});
        jCurl.setBody(testBody);
        jCurl.setVerbose(true);
        jCurl.run();
    }

    @Test
    public void testJCurlPut() {
        String response = """
                {
                  "args": {},\s
                  "data": "{}",\s
                  "files": {},\s
                  "form": {},\s
                  "headers": {
                    "Accept-Encoding": "gzip",\s
                    "Content-Length": "2",\s
                    "Content-Type": "application/json; charset=utf-8",\s
                    "Host": "www.httpbin.org",\s
                    "User-Agent": "okhttp/3.14.0",\s
                    "X-Amzn-Trace-Id": "Root=1-66ba25b6-0e001e9762d6ecdf3ebc50c6"
                  },\s
                  "json": {},\s
                  "origin": "100.8.48.199",\s
                  "url": "http://www.httpbin.org/put"
                }
                """;
        String testBody = """
                {
                   "Hello": "World"
                 }
                """;
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        JCurl jCurl = new JCurl();
        jCurl.setUrl(baseUrl+PUT_PATH);
        jCurl.setMethod(PUT_PATH);
        jCurl.setHeaders(new String[]{"Accept:applicatio/json"});
        jCurl.setBody(testBody);
        jCurl.setVerbose(true);
        jCurl.run();
    }

    @Test
    public void testSadPathMalformedUrl() {
        JCurl jCurl = new JCurl();
        jCurl.setUrl("Not_A_Valid_URL");
        jCurl.setMethod(GET_PATH);
        jCurl.setVerbose(true);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                jCurl::run,
                "Expected run() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to execute jCurl"));
    }

    @Test
    public void testNotYetImplemented() {
        JCurl jCurl = new JCurl();
        jCurl.setUrl(baseUrl);
        jCurl.setMethod(PATCH);
        jCurl.setVerbose(true);
        Assertions.assertDoesNotThrow(jCurl::run);
    }
}
