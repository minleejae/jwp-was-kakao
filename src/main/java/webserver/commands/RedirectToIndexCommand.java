package webserver.commands;

import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.response.HttpResponseHeaderBuilder;

public class RedirectToIndexCommand implements HttpRequestCommand {
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        Header header = new HttpResponseHeaderBuilder.Builder()
                .location("http://localhost:8080/index.html")
                .build();

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND)
                .header(header)
                .build();
    }
}
