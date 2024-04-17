package webserver.commands;

import db.DataBase;
import http.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.response.HttpResponseHeaderBuilder;
import http.session.Session;
import http.session.SessionManager;
import model.User;

import java.util.UUID;

import static http.response.HttpResponseBuilder.createRedirectResponse;
import static service.UserService.isValidUser;

public class LoginUserCommand implements HttpRequestCommand {

    public LoginUserCommand() {
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        QueryParams queryParams = extractQueryParams(httpRequest);

        String userId = queryParams.get("userId");
        String password = queryParams.get("password");

        User user = DataBase.findUserById(userId);

        if (!isValidUser(user, password)) {
            return createRedirectResponse("/user/login_failed.html");
        }

        return createRedirectResponseWithCookie(user, "/index.html", generateSessionId());
    }

    private QueryParams extractQueryParams(HttpRequest httpRequest) {
        String requestBody = httpRequest.getBody();
        return QueryParams.of(requestBody);
    }

    private HttpResponse createRedirectResponseWithCookie(User user, String location, String sessionId) {
        Session newSession = new Session(sessionId);
        newSession.setAttribute("userId", user.getUserId());
        SessionManager.getInstance().add(newSession);

        HttpCookie httpCookie = HttpCookie.createBySessionId(sessionId, "/");

        Header header = new HttpResponseHeaderBuilder.Builder()
                .location(location)
                .cookie(httpCookie)
                .build();

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND)
                .header(header)
                .build();
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
