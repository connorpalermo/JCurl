package com.jcurl;

import com.jcurl.cli.JCurl;
import picocli.CommandLine;

public class JCurlApplication {

    public static void main(String[] args){
        int exitCode = new CommandLine(new JCurl()).execute(args);
        System.exit(exitCode);
    }

}
