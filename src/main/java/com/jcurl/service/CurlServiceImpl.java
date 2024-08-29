package com.jcurl.service;

import okhttp3.*;

import java.io.IOException;

public class CurlServiceImpl implements CurlService {
    private final OkHttpClient client;

    public CurlServiceImpl(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public Response curlGet(String url, String[] headers) {
        try {
            Request request = new Request.Builder().url(url).headers(generateHeaders(headers)).build();
            return client.newCall(request).execute();
        } catch (IOException e){
            throw new RuntimeException("Failed to execute request", e);
        }
    }

    public Response curlDelete(String url, String[] headers){
        try {
            Request request = new Request.Builder().url(url).headers(generateHeaders(headers)).delete().build();
            return client.newCall(request).execute();
        } catch (IOException e){
            throw new RuntimeException("Failed to execute request", e);
        }
    }

    public Response curlPost(String url, String body, String[] headers){
        try {
            RequestBody rb = RequestBody.create(MediaType.parse("application/json"), body);
            Request request = new Request.Builder().url(url).headers(generateHeaders(headers)).post(rb).build();
            return client.newCall(request).execute();
        } catch (IOException e){
            throw new RuntimeException("Failed to execute request", e);
        }
    }

    public Response curlPut(String url, String body, String[] headers){
        try {
            RequestBody rb = RequestBody.create(MediaType.parse("application/json"), body);
            Request request = new Request.Builder().url(url).headers(generateHeaders(headers)).put(rb).build();
            return client.newCall(request).execute();
        } catch (IOException e){
            throw new RuntimeException("Failed to execute request", e);
        }
    }

    private Headers generateHeaders(String[] httpHeaders){
        if(httpHeaders == null) return new Headers.Builder().build();
        Headers.Builder headers = new Headers.Builder();
        for (String httpHeader : httpHeaders) {
            String[] headerSplit = httpHeader.split(":");
            headers.add(headerSplit[0], headerSplit[1]);
        }
        return headers.build();
    }
}
