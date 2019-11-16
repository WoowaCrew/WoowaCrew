package woowacrew.keyword.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.keyword.exception.NotFoundKeyword;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeywordServiceTest {
    @Autowired
    private KeywordService keywordService;

    @Test
    void 정상적으로_검색어_저장() {
        assertThat(keywordService.save("test")).isEqualTo(1L);
    }

    @Test
    void 저장된_검색어_찾기() {
        long keywordId = keywordService.save("test");
        assertDoesNotThrow(() -> keywordService.findById(keywordId));
    }

    @Test
    @DisplayName("존재하지 않는 검색어를 찾는 경우 예외가 발생한다.")
    void 없는_검색어를_찾기() {
        assertThrows(NotFoundKeyword.class, () -> keywordService.findById(0L));
    }

    @Test
    @DisplayName("중복된 검색어인 경우 조회수를 올린다.")
    void 중복된_검색어를_저장() {
        String duplicateKeyword = "중복된 검색어";

        keywordService.save(duplicateKeyword);
        long keywordId = keywordService.save(duplicateKeyword);

        assertThat(keywordService.findById(keywordId).getViews()).isEqualTo(2L);
    }
}