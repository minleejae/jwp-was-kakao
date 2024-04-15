package webserver;

import http.HttpMethod;
import http.HttpStatus;
import http.HttpVersion;
import webserver.commands.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {

    private final HttpRequest httpRequest;

    private static final Map<String, HttpRequestCommand> routeTable = new HashMap<>();

    static {
        routeTable.put("GET:/", new RedirectToIndexCommand());
        routeTable.put("GET:/*", new GetRequestCommand());
        routeTable.put("POST:/user/create", new CreateUserCommand());
        routeTable.put("POST:/user/login", new LoginUserCommand());
    }

    public HttpRequestHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse handle() throws IOException, URISyntaxException {
        HttpMethod method = httpRequest.getHttpMethod();
        String path = httpRequest.getEndPoint();
        String key = method.toString() + ":/*";

        HttpRequestCommand command = routeTable.get(key);

        String specificKey = method + ":" + path;
        if (routeTable.containsKey(specificKey)) {
            command = routeTable.get(specificKey);
        }

        if (command != null) {
            return command.handle(httpRequest);
        }

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}
