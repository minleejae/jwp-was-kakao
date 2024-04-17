package http.request;

import http.Body;
import http.Header;
import http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpStartLineUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestBuilder.class);
    private final BufferedReader bufferedReader;
    private RequestStartLine requestStartLine;
    private Header header;
    private Body body;

    public HttpRequestBuilder(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public HttpRequest build() throws IOException {
        logger.debug("Start HttpRequest");
        requestStartLine = buildStartLine();
        header = buildHeader();
        body = buildBody();
        logger.debug("Stop HttpRequest");
        return new HttpRequest(requestStartLine, header, body);
    }

    private RequestStartLine buildStartLine() throws IOException {
        String line = bufferedReader.readLine();
        logger.debug(line);
        return HttpStartLineUtils.parse(line);
    }

    private Header buildHeader() throws IOException {
        final int KEY_INDEX = 0;
        final int VALUE_INDEX = 1;
        String line;

        Header header = new Header();
        while (true) {
            line = bufferedReader.readLine();
            logger.debug(line);
            if (line == null || line.isEmpty()) {
                break;
            }
            String[] split = line.split(": ");
            header.put(split[KEY_INDEX], split[VALUE_INDEX]);
        }

        return header;
    }

    private Body buildBody() throws IOException {
        if (requestStartLine.getHttpMethod().equals(HttpMethod.GET)) {
            return null;
        }

        String bodyString = IOUtils.readData(bufferedReader, header.getContentLength());
        logger.debug(bodyString);
        return new Body(bodyString);
    }
}
