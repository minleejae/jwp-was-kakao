package http;

import db.DataBase;
import model.User;
import utils.ContentTypeParser;
import utils.FileIoUtils;
import utils.HttpResponseBuilder;
import utils.TemplateUrlBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequestHandler {

    private HttpRequest httpRequest;

    public HttpRequestHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse handle() throws IOException, URISyntaxException {
        HttpMethod httpMethod = httpRequest.getHttpMethod();
        String endPoint = httpRequest.getEndPoint();
        String url = httpRequest.getUrl();
        Header header = new Header();
        byte[] body;

        if (httpMethod.equals(HttpMethod.GET)) {
            String templateUrl = TemplateUrlBuilder.build(url);
            ContentType contentType = ContentTypeParser.parse(templateUrl);

            body = FileIoUtils.loadFileFromClasspath(templateUrl);

            header.put("Content-Length", String.valueOf(body.length));
            header.put("Content-Type", contentType.getValue() + ";charset=utf-8");

            return HttpResponseBuilder.builder()
                    .httpVersion(HttpVersion.HTTP_1_1)
                    .httpStatus(HttpStatus.OK)
                    .body(body)
                    .header(header)
                    .build();
        }

        if (httpMethod.equals(HttpMethod.POST)) {
            String requestbody = httpRequest.getBody();
            if (endPoint.equals("/user/create")) {
                QueryParams queryParams = QueryParams.of(requestbody);
                User user = new User(queryParams.get("userId"),
                        queryParams.get("password"),
                        queryParams.get("name"),
                        queryParams.get("email"));

                DataBase.addUser(user);

                header.put("location", "http://localhost:8080/index.html");

                return HttpResponseBuilder.builder()
                        .httpVersion(HttpVersion.HTTP_1_1)
                        .httpStatus(HttpStatus.FOUND)
                        .header(header)
                        .build();
            }
        }

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}


