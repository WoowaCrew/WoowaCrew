package woowacrew.keyword.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.keyword.domain.Keyword;
import woowacrew.keyword.domain.KeywordDto;
import woowacrew.keyword.domain.KeywordRepository;
import woowacrew.keyword.domain.KeywordResponse;
import woowacrew.keyword.exception.NotFoundKeyword;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @Mock
    private KeywordRepository mockKeywordRepository;

    @InjectMocks
    private KeywordService mockKeywordService;

    @Test
    void 정상적으로_검색어_저장() {
        KeywordDto keywordDto = new KeywordDto("test");
        assertThat(keywordService.save(keywordDto)).isNotNull();
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
        KeywordDto keywordDto = new KeywordDto("중복된 검색어");
        KeywordResponse keywordResponse = keywordService.save(keywordDto);

        assertThat(keywordService.findById(keywordResponse.getId()).getViews()).isEqualTo(2L);
    }

    @Test
    @DisplayName("조회수가 높은 순으로 검색어를 10개 정렬한다.")
    void 인기순으로_검색어를_10개_찾기() {
        List<KeywordResponse> keywords = keywordService.keywordRank();

        assertTrue(keywords.size() <= 10);

        assertThat(keywords.get(0).getContent()).isEqualTo("최다 조회수 A");
        assertThat(keywords.get(1).getContent()).isEqualTo("최다 조회수 B");
        assertThat(keywords.get(2).getContent()).isEqualTo("최다 조회수 C");
    }

    @Test
    void 검색어_조회수_증가() {
        Keyword keyword = new Keyword("검색어 조회수 증가");

        when(mockKeywordRepository.findById(anyLong())).thenReturn(Optional.of(keyword));

        assertThat(mockKeywordService.increaseViews(1L).getContent()).isEqualTo(keyword.getContent());
        assertTrue(keyword.getViews() != 0);
    }

    @Test
    @DisplayName("존재하지 않는 검색어를 증가시키는 경우 예외가 발생한다.")
    void 없는_검색어를_증가시키는_경우() {
        when(mockKeywordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundKeyword.class, () -> mockKeywordService.increaseViews(1L));
    }
}