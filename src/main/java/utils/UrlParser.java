package utils;

public class UrlParser {
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int QUERY_STRING_INDEX = 1;

    public static String parseToQueryString(String url) {
        String[] queryStrings = url.split(QUERY_STRING_DELIMITER);
        return queryStrings[QUERY_STRING_INDEX];
    }
}
