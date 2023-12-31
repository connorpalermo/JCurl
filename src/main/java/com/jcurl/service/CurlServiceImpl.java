package com.jcurl.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurlServiceImpl implements CurlService {

    private final RestTemplate curlServiceRestTemplate;

    public CurlServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.curlServiceRestTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> curlGet(String url, String[] headers){
        return curlServiceRestTemplate.exchange(url, HttpMethod.GET, generateEntity(headers, null), String.class);
    }

    public ResponseEntity<String> curlDelete(String url, String[] headers){
        return curlServiceRestTemplate.exchange(url, HttpMethod.DELETE, generateEntity(headers, null), String.class);
    }

    public ResponseEntity<String> curlPost(String url, String body, String[] headers){
        return curlServiceRestTemplate.exchange(url, HttpMethod.POST, generateEntity(headers, body), String.class);
    }

    public ResponseEntity<String> curlPut(String url, String body, String[] headers){
        return curlServiceRestTemplate.exchange(url, HttpMethod.PUT, generateEntity(headers, body), String.class);
    }

    private HttpEntity<String> generateEntity(String[] httpHeaders, String body){
        HttpHeaders headers = new HttpHeaders();
        for (String httpHeader : httpHeaders) {
            String[] headerSplit = httpHeader.split(":");
            headers.add(headerSplit[0], headerSplit[1]);
        }
        return new HttpEntity<>(body, headers);
    }
}
