package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Header {
    private final Map<String, String> map = new HashMap<>();

    public Header() {
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(map.get("Content-Length"));
    }

    public Set<String> getKeys() {
        return map.keySet();
    }
}
