package http.request;

import http.Body;
import http.Header;
import http.HttpMethod;

public class HttpRequest {
    private final RequestStartLine requestStartLine;
    private final Header header;
    private final Body body;

    public HttpRequest(RequestStartLine requestStartLine, Header header, Body body) {
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

    public Header getHeader() {
        return this.header;
    }

    public String getBody() {
        return body.getBody();
    }
}
