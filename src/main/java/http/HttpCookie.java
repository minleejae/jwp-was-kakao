package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class HttpCookie {
    private static final String COOKIE_PAIR_DELIMITER = ";\\s*";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    public static final int COOKIE_PREFIX_LENGTH = 8;
    private static final String COOKIE_PREFIX = "Cookie: ";
    private static final String SESSION_COOKIE_KEY = "JSESSIONID";
    private static final String PATH_KEY = "Path";

    private final Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie from(String header) {
        if (header.startsWith(COOKIE_PREFIX)) {
            header = header.substring(COOKIE_PREFIX_LENGTH);
        }

        String[] entries = header.split(COOKIE_PAIR_DELIMITER);

        Map<String, String> cookiesMap = Arrays.stream(entries)
                .map(entry -> entry.split(NAME_VALUE_DELIMITER))
                .filter(parts -> parts.length >= 2)
                .collect(Collectors.toMap(parts -> parts[KEY_INDEX], parts -> parts[VALUE_INDEX]));

        return new HttpCookie(cookiesMap);
    }

    public static HttpCookie createBySessionId(String sessionId, String path) {
        Map<String, String> cookiesMap = new HashMap<>();
        cookiesMap.put(SESSION_COOKIE_KEY, sessionId);
        cookiesMap.put(PATH_KEY, path);
        return new HttpCookie(cookiesMap);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public String getSessionId() {
        return cookies.get(SESSION_COOKIE_KEY);
    }

    public String getPath() {
        return cookies.get(PATH_KEY);
    }

    public Map<String, String> getCookies() {
        return new HashMap<>(cookies);
    }
}
