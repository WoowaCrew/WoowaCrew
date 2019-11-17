package woowacrew.keyword.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.keyword.domain.Keyword;
import woowacrew.keyword.exception.NotFoundKeyword;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @Test
    void 정상적으로_검색어_저장() {
        assertThat(keywordService.save("test")).isNotNull();
    }

    @Test
    void 저장된_검색어_찾기() {
        assertDoesNotThrow(() -> keywordService.findById(1L));
    }

    @Test
    @DisplayName("존재하지 않는 검색어를 찾는 경우 예외가 발생한다.")
    void 없는_검색어를_찾기() {
        assertThrows(NotFoundKeyword.class, () -> keywordService.findById(0L));
    }

    @Test
    @DisplayName("존재하는 검색어를 저장하면 조회수를 올린다.")
    void 중복된_검색어를_저장() {
        long keywordId = keywordService.save("중복된 검색어");

        assertThat(keywordService.findById(keywordId).getViews()).isEqualTo(2L);
    }

    @Test
    @DisplayName("조회수가 높은 순으로 검색어를 10개 정렬한다.")
    void 인기순으로_검색어를_10개_찾기() {
        List<Keyword> keywords = keywordService.getKeywordRank();

        assertTrue(keywords.size() <= 10);

        assertThat(keywords.get(0).getContent()).isEqualTo("최다 조회수 A");
        assertThat(keywords.get(1).getContent()).isEqualTo("최다 조회수 B");
        assertThat(keywords.get(2).getContent()).isEqualTo("최다 조회수 C");
    }
}