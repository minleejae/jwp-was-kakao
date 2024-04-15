package utils;

import http.HttpMethod;
import http.request.RequestStartLine;

public class HttpHeaderUtils {

    private static final String HEADER_PARTS_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;

    private HttpHeaderUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static RequestStartLine parse(String header) {
        HttpMethod httpMethod = parseHttpMethod(header);
        String url = parseUrl(header);

        return new RequestStartLine(httpMethod, url);
    }

    public static HttpMethod parseHttpMethod(String header) {
        String[] s = header.split(HEADER_PARTS_DELIMITER);
        return HttpMethod.of(s[HTTP_METHOD_INDEX]);
    }

    public static String parseUrl(String header) {
        String[] s = header.split(HEADER_PARTS_DELIMITER);
        return s[URL_INDEX];
    }
}
