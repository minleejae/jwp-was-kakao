package webserver.commands;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpRequestCommand {
    HttpResponse handle(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
