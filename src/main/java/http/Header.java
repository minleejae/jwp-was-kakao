package http;

import java.util.Map;
import java.util.Set;

public class Header {
    private final Map<String, String> headers;

    public Header(Map<String, String> headers) {
        this.headers = headers;
    }
    
    public String get(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public Set<String> getKeys() {
        return headers.keySet();
    }
}