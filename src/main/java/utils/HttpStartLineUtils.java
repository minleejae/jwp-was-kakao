package utils;

import http.HttpMethod;
import http.request.Protocol;
import http.request.RequestStartLine;
import http.request.Url;

public class HttpStartLineUtils {

    private static final String HEADER_PARTS_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;

    private static final int PROTOCOL_INDEX = 2;

    private HttpStartLineUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static RequestStartLine parse(String startLine) {
        String[] split = startLine.split(HEADER_PARTS_DELIMITER);

        HttpMethod httpMethod = HttpMethod.of(split[HTTP_METHOD_INDEX]);
        Url url = new Url(split[URL_INDEX]);
        Protocol protocol = Protocol.from(split[PROTOCOL_INDEX]);

        return new RequestStartLine(httpMethod, url, protocol);
    }
}
