package webserver.commands;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
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
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static http.response.HttpResponseBuilder.createNotFoundResponse;
import static http.response.HttpResponseBuilder.createRedirectResponse;

public class GetUsersCommand implements HttpRequestCommand {

    private static final Logger log = LoggerFactory.getLogger(GetUsersCommand.class);

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        // 사용자가 로그인한 상태인지 확인
        if (!isUserLoggedIn(httpRequest)) {
            return createRedirectResponse("/login.html");
        }

        try {
            List<User> users = new ArrayList<>(DataBase.findAll());

            List<Map<String, Object>> indexedUsers = IntStream.range(0, users.size())
                    .mapToObj(index -> Map.of("index", index + 1, "user", users.get(index)))
                    .collect(Collectors.toList());

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/list");
            byte[] body = template.apply(Map.of("users", indexedUsers)).getBytes();

            Header header = new HttpResponseHeaderBuilder.Builder()
                    .contentLength(body.length)
                    .contentType(ContentType.TEXT_HTML)
                    .build();

            return HttpResponseBuilder.builder()
                    .httpVersion(HttpVersion.HTTP_1_1)
                    .httpStatus(HttpStatus.OK)
                    .body(body)
                    .header(header)
                    .build();

        } catch (Exception e) {
            log.error("Error rendering user list", e);
            return createNotFoundResponse();
        }
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
}
