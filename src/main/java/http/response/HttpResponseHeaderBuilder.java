package http.response;

import http.ContentType;
import http.Header;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeaderBuilder {

    public static class Builder {
        private final Map<String, String> headers = new HashMap<>();

        public Builder location(String url) {
            this.headers.put("Location", url);
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.headers.put("Content-Type", contentType.getValue());
            return this;
        }

        public Builder contentLength(long length) {
            this.headers.put("Content-Length", String.valueOf(length));
            return this;
        }

        public Builder cookie(String cookie) {
            this.headers.put("Set-Cookie", cookie);
            return this;
        }

        public Header build() {
            return new Header(this.headers);
        }
    }
}
