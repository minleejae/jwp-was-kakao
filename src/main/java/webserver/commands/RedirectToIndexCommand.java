package webserver.commands;

import http.Header;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.HttpStatus;
import http.HttpVersion;

public class RedirectToIndexCommand implements HttpRequestCommand {
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        Header header = new Header();
        header.put("location", "http://localhost:8080/index.html");

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND) // 302 Redirect
                .header(header)
                .build();
    }
}
