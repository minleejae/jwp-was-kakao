package webserver;

import http.HttpMethod;
import http.QueryParams;
import model.User;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import utils.HttpHeaderUtils;
import utils.QueryStringParser;
import utils.UrlParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class RequestHandlerTest {

    private static final String TEMPLATE_PATH = "./templates";
    private static final String HEADER = "GET /index.html HTTP/1.1";

    @Test
    void 헤더에서_타입_추출하기() {
        HttpMethod httpMethod = HttpHeaderUtils.parseHttpMethod(HEADER);

        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }

    @Test
    void 헤더에서_URL_추출하기() {
        String url = HttpHeaderUtils.parseUrl(HEADER);

        assertThat(url).isEqualTo("/index.html");
    }

    @Test
    void 헤더에서_URL_추출해서_해당하는_파일_읽기() throws IOException, URISyntaxException {
        String url = HttpHeaderUtils.parseUrl(HEADER);

        String filePath = TEMPLATE_PATH + url;

        byte[] bytes = FileIoUtils.loadFileFromClasspath(filePath);

        assertThat(filePath).isEqualTo("./templates/index.html");
        assertThat(bytes).isNotEmpty();
    }

    @Test
    void QUERY_STRING_파싱() {
        String header = "GET /user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com HTTP/1.1\n";

        String url = HttpHeaderUtils.parseUrl(header);
        String queryString = UrlParser.parseToQueryString(url);
        Map<String, String> data = QueryStringParser.parseToMap(queryString);

        QueryParams queryParams = new QueryParams(data);

        assertThat(queryParams.get("userId")).isEqualTo("cu");
        assertThat(queryParams.get("password")).isEqualTo("password");
        assertThat(queryParams.get("name")).isEqualTo("%EC%9D%B4%EB%8F%99%EA%B7%9C");
        assertThat(queryParams.get("email")).isEqualTo("brainbackdoor%40gmail.com");
    }


    @Test
    void QUERY_PARAM_MAPPER_테스트() {
        String header = "GET /user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com HTTP/1.1\n";

        String url = HttpHeaderUtils.parseUrl(header);
        String queryString = UrlParser.parseToQueryString(url);
        Map<String, String> data = QueryStringParser.parseToMap(queryString);

        QueryParams queryParams = new QueryParams(data);

        User user = new User(queryParams.get("userId"),
                queryParams.get("password"),
                queryParams.get("name"),
                queryParams.get("email"));

        assertThat(user.getUserId()).isEqualTo("cu");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("%EC%9D%B4%EB%8F%99%EA%B7%9C");
        assertThat(user.getEmail()).isEqualTo("brainbackdoor%40gmail.com");
    }
}