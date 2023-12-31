package com.jcurl.service;

import org.springframework.http.ResponseEntity;

public interface CurlService {
    ResponseEntity<String> curlGet(String url, String[] headers);
    ResponseEntity<String> curlDelete(String url, String[] headers);
    ResponseEntity<String> curlPost(String url, String body, String[] headers);
    ResponseEntity<String> curlPut(String url, String body, String[] headers);
}
