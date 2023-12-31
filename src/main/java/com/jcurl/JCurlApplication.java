package com.jcurl;

import com.jcurl.cli.JCurl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class JCurlApplication implements CommandLineRunner {

    private JCurl jcurl;

    public JCurlApplication(JCurl jcurl){
        this.jcurl = jcurl;
    }

    public static void main(String[] args){
        SpringApplication.run(JCurlApplication.class, args);
    }

    @Override
    public void run(String... args) {
        int exitCode = new CommandLine(jcurl).execute(args);
        System.exit(exitCode);
    }
}
