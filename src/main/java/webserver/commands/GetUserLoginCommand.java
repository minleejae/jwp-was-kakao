package webserver.commands;

import http.ContentType;
import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.response.HttpResponseHeaderBuilder;
import http.session.Session;
import http.session.SessionManager;
import utils.ContentTypeParser;
import utils.FileIoUtils;
import utils.TemplateUrlBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class GetUserLoginCommand implements HttpRequestCommand {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (isUserLoggedIn(httpRequest)) {
            return createRedirectResponse("http://localhost:8080/index.html");
        }

        String templateUrl = TemplateUrlBuilder.build(httpRequest.getUrl().getPath());
        ContentType contentType = ContentTypeParser.parse(templateUrl);
        byte[] body = FileIoUtils.loadFileFromClasspath(templateUrl);

        Header header = new HttpResponseHeaderBuilder.Builder()
                .contentLength(body.length)
                .contentType(contentType)
                .build();

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.OK)
                .body(body)
                .header(header)
                .build();
    }

    private boolean isUserLoggedIn(HttpRequest httpRequest) {
        String cookies = httpRequest.getHeader().get("Cookie");

        if (cookies == null || !cookies.contains("JSESSIONID")) {
            return false;
        }

        String[] cookiesArray = cookies.split(";\\s*");
        String sessionId = null;
        for (String cookie : cookiesArray) {
            if (cookie.startsWith("JSESSIONID=")) {
                sessionId = cookie.substring("JSESSIONID=".length());
                break;
            }
        }

        if (sessionId == null) {
            return false;
        }

        Session session = SessionManager.getInstance().findSession(sessionId);
        return session != null && session.getAttribute("userId") != null;
    }

    private HttpResponse createRedirectResponse(String location) {
        Header header = new HttpResponseHeaderBuilder.Builder()
                .location(location)
                .build();

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND)
                .header(header)
                .build();
    }
}
