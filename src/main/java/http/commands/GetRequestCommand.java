package http.commands;

import http.*;
import utils.ContentTypeParser;
import utils.FileIoUtils;
import utils.HttpResponseBuilder;
import utils.TemplateUrlBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class GetRequestCommand implements HttpRequestCommand {

    private final HttpRequest httpRequest;

    public GetRequestCommand(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse handle() throws IOException, URISyntaxException {
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
