package utils;

import http.HttpMethod;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {
    @Test
    void 메서드_생성() {
        HttpMethod httpMethod = HttpMethod.of("GET");
        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }

    @Test
    void 존재하지_않는_메서드() {
        assertThatThrownBy(() -> HttpMethod.of("PUT"))
                .isInstanceOf(NoSuchElementException.class);
    }
}