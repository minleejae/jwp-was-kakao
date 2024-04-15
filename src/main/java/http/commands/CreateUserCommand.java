package http.commands;

import db.DataBase;
import http.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import http.response.HttpResponseBuilder;

public class CreateUserCommand implements HttpRequestCommand {

    private final HttpRequest httpRequest;

    public CreateUserCommand(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse handle() {
        String requestBody = httpRequest.getBody();
        QueryParams queryParams = QueryParams.of(requestBody);

        User user = new User(
                queryParams.get("userId"),
                queryParams.get("password"),
                queryParams.get("name"),
                queryParams.get("email")
        );

        DataBase.addUser(user);

        Header header = new Header();
        header.put("location", "http://localhost:8080/index.html");

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND)
                .header(header)
                .build();
    }
}
