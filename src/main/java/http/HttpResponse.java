package http;

public class HttpResponse {
    private ResponseStartLine startLine;

    private Header header;

    private byte[] body;

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
