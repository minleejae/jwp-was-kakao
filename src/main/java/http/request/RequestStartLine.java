package http.request;

import http.HttpMethod;

public class RequestStartLine {
    private final HttpMethod httpMethod;
    private final String url;

    public RequestStartLine(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getEndPoint() {
        return url.split("\\?")[0];
    }
}
