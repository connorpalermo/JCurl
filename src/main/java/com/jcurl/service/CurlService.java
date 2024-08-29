package com.jcurl.service;


import okhttp3.Response;

public interface CurlService {
    Response curlGet(String url, String[] headers);
    Response curlDelete(String url, String[] headers);
    Response curlPost(String url, String body, String[] headers);
    Response curlPut(String url, String body, String[] headers);
}
