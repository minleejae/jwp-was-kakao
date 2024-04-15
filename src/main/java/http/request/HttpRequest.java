package http.request;

import http.Header;
import http.HttpMethod;

public class HttpRequest {
    private final RequestStartLine requestStartLine;
    private final Header header;
    private final String body;

    public HttpRequest(RequestStartLine requestStartLine, Header header, String body) {
        this.requestStartLine = requestStartLine;
        this.header = header;
        this.body = body;
    }

    public HttpMethod getHttpMethod() {
        return requestStartLine.getHttpMethod();
    }

    public String getUrl() {
        return requestStartLine.getUrl();
    }

    public String getEndPoint() {
        return requestStartLine.getEndPoint();
    }

    public String getBody() {
        return body;
    }
}
