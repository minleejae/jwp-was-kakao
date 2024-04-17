package webserver.route;

import http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RouteKeyFactory {
    private static final Map<String, RouteKey> cache = new HashMap<>();
    private static final String ROUTE_KEY_SEPARATOR = ":";

    public static RouteKey getRouteKey(HttpMethod method, String path) {
        String key = method.name() + ROUTE_KEY_SEPARATOR + path;
        if (!cache.containsKey(key)) {
            cache.put(key, new RouteKey(method, path));
        }
        return cache.get(key);
    }
}
