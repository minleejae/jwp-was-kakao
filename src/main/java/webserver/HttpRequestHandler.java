package webserver;

import http.HttpMethod;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import webserver.commands.*;
import webserver.route.RouteKey;
import webserver.route.RouteKeyFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {

    private final HttpRequest httpRequest;

    private static final Map<RouteKey, HttpRequestCommand> routeTable = new HashMap<>();

    public static final String ROOT = "/";
    public static final String USER_LIST = "/user/list.html";
    public static final String USER_LOGIN = "/user/login.html";
    public static final String USER_CREATE = "/user/create";
    public static final String USER_LOGIN_POST = "/user/login";
    public static final String WILDCARD = "/*";

    static {
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.GET, ROOT), new RedirectToIndexCommand());
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.GET, USER_LIST), new GetUsersCommand());
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.GET, USER_LOGIN), new GetUserLoginCommand());
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.GET, WILDCARD), new GetRequestCommand());
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.POST, USER_CREATE), new CreateUserCommand());
        routeTable.put(RouteKeyFactory.getRouteKey(HttpMethod.POST, USER_LOGIN_POST), new LoginUserCommand());
    }

    public HttpRequestHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse handle() throws IOException, URISyntaxException {
        HttpMethod method = httpRequest.getHttpMethod();
        String path = httpRequest.getUrl().getPath();

        RouteKey key = RouteKeyFactory.getRouteKey(method, WILDCARD);

        HttpRequestCommand command = routeTable.get(key);

        RouteKey specificKey = RouteKeyFactory.getRouteKey(method, path);
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
