package http.request;

import http.HttpMethod;

public class RequestStartLine {

    private static final String HEADER_PARTS_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    private final HttpMethod httpMethod;
    private final Url url;
    private final Protocol protocol;

    private RequestStartLine(HttpMethod httpMethod, Url url, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
    }

    public static RequestStartLine from(String startLine) {
        String[] split = startLine.split(HEADER_PARTS_DELIMITER);

        HttpMethod httpMethod = HttpMethod.of(split[HTTP_METHOD_INDEX]);
        Url url = new Url(split[URL_INDEX]);
        Protocol protocol = Protocol.from(split[PROTOCOL_INDEX]);

        return new RequestStartLine(httpMethod, url, protocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getUrl() {
        return url;
    }


    public Protocol getProtocol() {
        return protocol;
    }
}
