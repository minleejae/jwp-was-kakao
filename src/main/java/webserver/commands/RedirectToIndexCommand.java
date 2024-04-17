package webserver.commands;

import http.request.HttpRequest;
import http.response.HttpResponse;

import static http.response.HttpResponseBuilder.createRedirectResponse;

public class RedirectToIndexCommand implements HttpRequestCommand {
    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        return createRedirectResponse("/index.html");
    }
}
