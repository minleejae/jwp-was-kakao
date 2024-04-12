package utils;

import http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ContentTypeParserTest {

    @Test
    public void 파일경로에서_ContentType을_추출한다() {
        String filePath = "./static/css/styles.css";

        ContentType contentType = ContentTypeParser.parse(filePath);

        Assertions.assertThat(contentType).isEqualTo(ContentType.TEXT_CSS);
    }
}