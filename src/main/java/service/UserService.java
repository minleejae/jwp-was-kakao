package service;

import http.HttpCookie;
import http.request.HttpRequest;
import http.session.Session;
import http.session.SessionManager;
import model.User;

public class UserService {
    public static boolean isUserLoggedIn(HttpRequest httpRequest) {
        String cookiesString = httpRequest.getHeader().getCookie();
        if (cookiesString == null || cookiesString.isEmpty()) {
            return false;
        }

        HttpCookie httpCookie = HttpCookie.from(cookiesString);
        String sessionId = httpCookie.getSessionId();

        if (sessionId == null) {
            return false;
        }

        Session session = SessionManager.getInstance().findSession(sessionId);
        return session != null && session.getAttribute("userId") != null;
    }

    public static boolean isValidUser(User user, String password) {
        return user != null && password.equals(user.getPassword());
    }
}
