package http.commands;

import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpRequestCommand {
    HttpResponse handle() throws IOException, URISyntaxException;
}
