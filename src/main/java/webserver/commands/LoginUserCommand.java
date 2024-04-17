package webserver.commands;

import db.DataBase;
import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.QueryParams;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.response.HttpResponseHeaderBuilder;
import http.session.Session;
import http.session.SessionManager;
import model.User;

import java.util.UUID;

import static http.response.HttpResponseBuilder.createRedirectResponse;

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

    private boolean isValidUser(User user, String password) {
        return user != null && password.equals(user.getPassword());
    }

    private HttpResponse createRedirectResponseWithCookie(User user, String location, String sessionId) {
        Session newSession = new Session(sessionId);
        newSession.setAttribute("userId", user.getUserId());
        SessionManager.getInstance().add(newSession);

        Header header = new HttpResponseHeaderBuilder.Builder()
                .location(location)
                .cookie("JSESSIONID=" + sessionId + "; Path=/")
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
