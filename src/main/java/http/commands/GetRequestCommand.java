package http.commands;

import http.ContentType;
import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import utils.ContentTypeParser;
import utils.FileIoUtils;
import utils.TemplateUrlBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class GetRequestCommand implements HttpRequestCommand {

    public GetRequestCommand() {
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String templateUrl = TemplateUrlBuilder.build(httpRequest.getUrl());
        ContentType contentType = ContentTypeParser.parse(templateUrl);
        byte[] body = FileIoUtils.loadFileFromClasspath(templateUrl);

        Header header = new Header();
        header.put("Content-Length", String.valueOf(body.length));
        header.put("Content-Type", contentType.getValue() + ";charset=utf-8");

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.OK)
                .body(body)
                .header(header)
                .build();
    }
}
