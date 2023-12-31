package com.jcurl.cli;

import com.jcurl.service.CurlServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.net.MalformedURLException;
import java.net.URL;

@Command(
        name = "jcurl",
        description = "Java Implementation of CURL",
        mixinStandardHelpOptions = true,
        version = "0.1.0"
)
@Component
public class JCurl implements Runnable{

    @Option(names = "-X" , description = "HTTP Method to use")
    String method = "GET";

    @Option(names = "-d" , description = "Body for POST or PUT Request")
    String body = "{}";

    @Option(names = "-H" , description = "Headers for your Request")
    String[] headers = {};

    @Option(names = "--verbose", description = "Output Request/Response", arity = "0")
    boolean verbose;

    @Parameters(paramLabel = "<url>",
            description = "The URL you would like make a request against.")
    private String url = "http://www.google.com";

    private CurlServiceImpl curlService;

    public JCurl(CurlServiceImpl curlService){
        this.curlService = curlService;
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
            ResponseEntity<String> response;
            switch (method) {
                case "GET" -> response = curlService.curlGet(url, headers);
                case "DELETE" -> response = curlService.curlDelete(url, headers);
                case "POST" -> response = curlService.curlPost(url, body, headers);
                case "PUT" -> response = curlService.curlPut(url, body, headers);
                default -> {
                    System.out.println("Not yet implemented!");
                    return;
                }
            }
            if(verbose) {
                HttpHeaders headers = response.getHeaders();
                headers.forEach((key, value) -> System.out.println("< " + key + ":" + value));
            }
            System.out.println(response.getBody());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
