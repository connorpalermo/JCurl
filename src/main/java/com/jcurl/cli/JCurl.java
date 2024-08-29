package com.jcurl.cli;

import com.jcurl.service.CurlService;
import com.jcurl.service.CurlServiceImpl;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.URL;

@Command(
        name = "jcurl",
        description = "Java Implementation of CURL",
        mixinStandardHelpOptions = true,
        version = "0.1.0"
)
public class JCurl implements Runnable{

    @Option(names = "-X" , description = "HTTP Method to use")
    private String method = "GET";

    @Option(names = "-d" , description = "Body for POST or PUT Request")
    private String body = "{}";

    @Option(names = "-H" , description = "Headers for your Request")
    private String[] headers = {};

    @Option(names = "--verbose", description = "Output Request/Response", arity = "0")
    private boolean verbose;

    @Parameters(paramLabel = "<url>",
            description = "The URL you would like make a request against.")
    private String url = "http://www.google.com";

    private final CurlService curlService;

    public JCurl(){
        this.curlService = new CurlServiceImpl(new OkHttpClient());
    }

    @Override
    public void run() {
        try {
            URL curlURL = new URL(url);
            String protocol = curlURL.getProtocol();
            String host = curlURL.getHost();
            String path = curlURL.getPath();
            if(verbose) {
                System.out.println("> connecting to: " + path);
                System.out.println("> Host: " + host);
                System.out.println("> Sending request " + method + " with protocol: " + protocol);
                System.out.println(">");
            }
            Response response;
            switch (method.toUpperCase()) {
                case "GET" -> response = curlService.curlGet(url, headers);
                case "DELETE" -> response = curlService.curlDelete(url, headers);
                case "POST" -> response = curlService.curlPost(url, body, headers);
                case "PUT" -> response = curlService.curlPut(url, body, headers);
                default -> {
                    System.out.println("Method nDot yet implemented!");
                    return;
                }
            }
            if(response == null) return;
            if(verbose) {
                Headers headers = response.headers();
                headers.toMultimap().forEach((key, value) -> System.out.println("< " + key + ":" + value.get(0)));
            }
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute jCurl", e);
        }
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
