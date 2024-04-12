package http;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod of(String type) {
        Arrays.stream(values())
                .filter(value -> value.name().equals(type.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메서드입니다."));

        return HttpMethod.valueOf(type.toUpperCase());
    }
}
