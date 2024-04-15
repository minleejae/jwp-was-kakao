package webserver.commands;

import db.DataBase;
import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.QueryParams;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import model.User;

public class CreateUserCommand implements HttpRequestCommand {

    public CreateUserCommand() {
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
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
