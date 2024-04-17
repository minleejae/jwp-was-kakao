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

        User user = new User(
                queryParams.get("userId"),
                queryParams.get("password"),
                queryParams.get("name"),
                queryParams.get("email")
        );

        DataBase.addUser(user);

        return createRedirectResponse("/index.html");
    }
}
