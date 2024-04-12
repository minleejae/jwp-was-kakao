package utils;

import http.*;

public class HttpResponseBuilder {

    private ResponseStartLine startLine;
    private Header header;
    private byte[] body;

    private HttpResponseBuilder(ResponseStartLine startLine, Header header, byte[] body) {
        this.startLine = startLine;
        this.header = header;
        this.body = body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HttpVersion httpVersion;
        private HttpStatus httpStatus;
        private Header header;
        private byte[] body;

        private Builder() {
        }

        public Builder httpVersion(HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder header(Header header) {
            this.header = header;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            ResponseStartLine startLine = new ResponseStartLine(httpVersion, httpStatus);
            return new HttpResponse(startLine, header, body);
        }
    }
}
