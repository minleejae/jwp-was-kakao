package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {
    @Test
    void 쿠키_생성(){
        String header = "Cookie: JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46; Path=/";

        HttpCookie httpCookie = HttpCookie.from(header);
        assertThat(httpCookie.getSessionId()).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46");
        assertThat(httpCookie.getPath()).isEqualTo("/");

    }
}