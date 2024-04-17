package http.response;

import http.ContentType;
import http.Header;
import http.HttpCookie;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeaderBuilder {

    public static class Builder {
        private static final String LOCATION = "Location";
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String CONTENT_LENGTH = "Content-Length";
        private static final String SET_COOKIE = "Set-Cookie";
        private static final String SESSION_COOKIE_KEY = "JSESSIONID";
        private static final String PATH_KEY = "Path";
        private static final String COOKIE_VALUE_SEPARATOR = "=";
        private static final String COOKIE_DELIMITER = ";";

        private final Map<String, String> headers = new HashMap<>();

        public Builder location(String url) {
            this.headers.put(LOCATION, url);
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.headers.put(CONTENT_TYPE, contentType.getValue());
            return this;
        }

        public Builder contentLength(long length) {
            this.headers.put(CONTENT_LENGTH, String.valueOf(length));
            return this;
        }

        public Builder cookie(HttpCookie cookie) {
            String sessionId = cookie.getSessionId();
            String path = cookie.getPath();

            StringBuilder cookieBuilder = new StringBuilder();
            if (sessionId != null) {
                cookieBuilder.append(SESSION_COOKIE_KEY).append(COOKIE_VALUE_SEPARATOR).append(sessionId);
            }
            if (path != null) {
                if (cookieBuilder.length() > 0) {
                    cookieBuilder.append(COOKIE_DELIMITER);
                }
                cookieBuilder.append(PATH_KEY).append(COOKIE_VALUE_SEPARATOR).append(path);
            }

            this.headers.put(SET_COOKIE, cookieBuilder.toString());
            return this;
        }

        public Header build() {
            return new Header(this.headers);
        }
    }
}
