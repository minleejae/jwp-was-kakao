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
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

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
        if (!UserService.isUserLoggedIn(httpRequest)) {
            return createRedirectResponse("/user/login.html");
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
}
