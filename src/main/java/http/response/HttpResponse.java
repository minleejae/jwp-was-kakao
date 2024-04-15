package http.response;

import http.Header;

public class HttpResponse {
    private final ResponseStartLine startLine;

    private final Header header;

    private final byte[] body;

    public HttpResponse(ResponseStartLine startLine, Header header, byte[] body) {
        this.startLine = startLine;
        this.header = header;
        this.body = body;
    }

    public ResponseStartLine getStartLine() {
        return startLine;
    }

    public Header getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }
}
