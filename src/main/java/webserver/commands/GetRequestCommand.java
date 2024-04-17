package webserver.commands;

import http.ContentType;
import http.Header;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.request.Url;
import http.response.HttpResponse;
import http.response.HttpResponseBuilder;
import http.response.HttpResponseHeaderBuilder;
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
        Url url = httpRequest.getUrl();
        String templateUrl = TemplateUrlBuilder.build(url.getPath());
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
}
