package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Map<String, String> parseToMap(String queryString) {
        String[] data = queryString.split(PARAMETER_DELIMITER);
        return Arrays.stream(data)
                .map(it -> it.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(strings -> strings[KEY_INDEX], strings -> strings[VALUE_INDEX]));
    }
}
