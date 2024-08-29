# Java Curl
A simple implementation of curl in Java. Using Picocli.
### How to use: 
JCurl is exceptionally easy to use, simply run the following commands from the root directory:
```
mvn clean compile assembly:single
alias jcurl='java -cp "target/jCurl-1.0-SNAPSHOT-jar-with-dependencies.jar" com.jcurl.JCurlApplication
```
You can add the above alias to your ~/.bashrc file if you don't want to keep running it. In future, may use a launcher script such as the [Application Assembler Maven Plugin](https://www.mojohaus.org/appassembler/) to avoid the alias.

You can now run JCurl. Here are some examples:
```
jcurl -X POST -d '{"Hello":"World"}' http://www.httpbin.org/post
jcurl --request POST --data '{"Hello":"World"}' http://www.httpbin.org/post 
jcurl -X POST http://www.httpbin.org/post -d '{"key": "value"}' -H "Content-Type: application/json"
jcurl --help
```
The first two options do the same thing, -d is shorthand for --data and -X is shorthand for --request. There is another option for headers, which you can add with -H or --header. You can also input --help for more help and information.

Happy JCurling!
