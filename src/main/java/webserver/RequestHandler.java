package webserver;

import http.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import utils.HttpRequestBuilder;
import http.HttpResponse;
import utils.ResponseWriter;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder(bufferedReader);
            HttpRequest httpRequest = httpRequestBuilder.build();

            HttpRequestHandler handler = new HttpRequestHandler(httpRequest);
            HttpResponse httpResponse = handler.handle();

            ResponseWriter responseWriter = new ResponseWriter(dos, httpResponse);
            responseWriter.write();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
