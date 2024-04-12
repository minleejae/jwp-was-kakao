package http;

import utils.QueryStringParser;

import java.util.Map;

public class QueryParams {
    private final Map<String, String> map;

    public QueryParams(Map<String, String> map) {
        this.map = map;
    }

    public static QueryParams of(String url) {
        Map<String, String> data = QueryStringParser.parseToMap(url);
        return new QueryParams(data);
    }

    public String get(String key) {
        return map.get(key);
    }
}
