package utils;

import http.Header;
import http.HttpMethod;
import http.HttpRequest;
import http.RequestStartLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestBuilder {

    private final BufferedReader bufferedReader;
    private RequestStartLine requestStartLine;
    private Header header;
    private String body;

    public HttpRequestBuilder(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public HttpRequest build() throws IOException {
        requestStartLine = buildStartLine();
        header = buildHeader();
        body = buildBody();
        return new HttpRequest(requestStartLine, header, body);
    }

    private RequestStartLine buildStartLine() throws IOException {
        String line = bufferedReader.readLine();
        return HttpHeaderUtils.parse(line);
    }

    private Header buildHeader() throws IOException {
        final int KEY_INDEX = 0;
        final int VALUE_INDEX = 1;
        String line;

        Header header = new Header();
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            String[] split = line.split(": ");
            header.put(split[KEY_INDEX], split[VALUE_INDEX]);
        }

        return header;
    }

    private String buildBody() throws IOException {
        if (requestStartLine.getHttpMethod().equals(HttpMethod.GET)) {
            return null;
        }

        return IOUtils.readData(bufferedReader, header.getContentLength());
    }
}
