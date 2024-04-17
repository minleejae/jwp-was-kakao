package http.request;

import http.HttpMethod;

public class RequestStartLine {
    private final HttpMethod httpMethod;
    private final Url url;
    private final Protocol protocol;

    public RequestStartLine(HttpMethod httpMethod, Url url, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Url getUrl() {
        return url;
    }

    public String getEndPoint() {
        return url.getEndPoint();
    }

    public String getProtocol() {
        return protocol.getVersion();
    }
}
