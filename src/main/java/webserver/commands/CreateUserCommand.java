package webserver.commands;

import db.DataBase;
import http.QueryParams;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static http.response.HttpResponseBuilder.createRedirectResponse;

public class CreateUserCommand implements HttpRequestCommand {

    public CreateUserCommand() {
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String requestBody = httpRequest.getBody();
        QueryParams queryParams = QueryParams.of(requestBody);

        User user = DataBase.findUserById(queryParams.get("userId"));

        if (user != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        User newUser = new User(
                queryParams.get("userId"),
                queryParams.get("password"),
                queryParams.get("name"),
                queryParams.get("email")
        );

        DataBase.addUser(newUser);

        return createRedirectResponse("/index.html");
    }
}
