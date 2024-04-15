package http;

import http.commands.CreateUserCommand;
import http.commands.GetRequestCommand;
import http.commands.HttpRequestCommand;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequestHandler {

    private final HttpRequest httpRequest;

    public HttpRequestHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse handle() throws IOException, URISyntaxException {
        HttpMethod httpMethod = httpRequest.getHttpMethod();
        String endPoint = httpRequest.getEndPoint();

        HttpRequestCommand command = getCommand(httpMethod, endPoint);
        if (command != null) {
            return command.handle(httpRequest);
        }

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    private HttpRequestCommand getCommand(HttpMethod httpMethod, String endPoint) {
        if (httpMethod.equals(HttpMethod.GET)) {
            return new GetRequestCommand();
        }

        if (httpMethod.equals(HttpMethod.POST) && endPoint.equals("/user/create")) {
            return new CreateUserCommand();
        }

        return null;
    }
}
