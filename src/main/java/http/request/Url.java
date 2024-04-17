package http.request;

public class Url {
    private static final String URL_DELIMITER = "\\?";
    private static final int END_POINT_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;
    private final String path;

    public Url(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public String getEndPoint() {
        return path.split("\\?")[END_POINT_INDEX];
    }

    public String getQueryString() {
        return path.split(URL_DELIMITER)[QUERY_STRING_INDEX];
    }
}
