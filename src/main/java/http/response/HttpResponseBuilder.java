package http.response;

import http.Header;
import http.HttpStatus;
import http.HttpVersion;

public class HttpResponseBuilder {

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

    public static HttpResponse createRedirectResponse(String locationUrl) {
        Header header = new HttpResponseHeaderBuilder.Builder()
                .location(locationUrl)
                .build();

        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.FOUND)
                .header(header)
                .build();
    }

    public static HttpResponse createNotFoundResponse(){
        return HttpResponseBuilder.builder()
                .httpVersion(HttpVersion.HTTP_1_1)
                .httpStatus(HttpStatus.NOT_FOUND)
                .body("Internal Server Error".getBytes())
                .build();
    }
}
