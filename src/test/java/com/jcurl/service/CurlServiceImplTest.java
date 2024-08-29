package com.jcurl.service;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurlServiceImplTest {

    private static final int OKAY_STATUS = 200;
    private String baseUrl;
    private static final String GET_PATH = "get";
    private static final String DELETE_PATH = "delete";
    private static final String POST_PATH = "post";
    private static final String PUT_PATH = "put";
    private CurlServiceImpl curlService;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        baseUrl = mockWebServer.url("").url().toString();
        curlService = new CurlServiceImpl(new OkHttpClient());
    }

    @Test
    public void test_getHappyPath() throws IOException {
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
        Response r = curlService.curlGet(baseUrl + GET_PATH, null);

        Assertions.assertNotNull(r.body());
        Assertions.assertEquals(r.body().string(), response);
    }

    @Test
    public void test_getWithHeadersHappyPath() throws IOException {
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
        Response r = curlService.curlGet(baseUrl + GET_PATH, new String[]{"Accept:application/json"});

        Assertions.assertNotNull(r.body());
        Assertions.assertEquals(r.body().string(), response);
    }

    @Test
    public void test_deleteHappyPath() throws IOException {
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
        Response r = curlService.curlDelete(baseUrl + DELETE_PATH, null);

        Assertions.assertNotNull(r.body());
        Assertions.assertEquals(r.body().string(), response);
    }

    @Test
    public void test_postHappyPath() throws IOException {
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
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        Response r = curlService.curlPost(baseUrl + POST_PATH, "",null);

        Assertions.assertNotNull(r.body());
        Assertions.assertEquals(r.body().string(), response);
    }

    @Test
    public void test_putHappyPath() throws IOException {
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
        mockWebServer.enqueue(new MockResponse().setBody(response).setResponseCode(OKAY_STATUS));
        Response r = curlService.curlPut(baseUrl + PUT_PATH, "",null);

        Assertions.assertNotNull(r.body());
        Assertions.assertEquals(r.body().string(), response);
    }

    @Test
    public void test_GetSadPathRuntimeException(){
        mockWebServer.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> curlService.curlGet(baseUrl + GET_PATH,null),
                "Expected curlGet() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to execute request"));
    }

    @Test
    public void test_PutSadPathRuntimeException(){
        mockWebServer.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> curlService.curlPut(baseUrl + PUT_PATH,"",null),
                "Expected curlPut() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to execute request"));
    }

    @Test
    public void test_PostSadRuntimeException(){
        mockWebServer.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> curlService.curlPost(baseUrl + POST_PATH,"",null),
                "Expected curlPost() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to execute request"));
    }

    @Test
    public void test_DeleteSadPathIOException(){
        mockWebServer.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST));

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> curlService.curlDelete(baseUrl + DELETE_PATH,null),
                "Expected curlDelete() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to execute request"));
    }
}
