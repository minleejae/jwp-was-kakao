package utils;

import http.ContentType;

import java.net.FileNameMap;
import java.net.URLConnection;

public class ContentTypeParser {

    private ContentTypeParser() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static ContentType parse(String filePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(filePath);
        return ContentType.of(contentType);
    }
}
