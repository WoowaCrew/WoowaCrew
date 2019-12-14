package woowacrew.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.exception.InvalidPageRequstException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleSearchInternalServiceTest {

    @Mock
    private ArticleRepository mockArticleRepository;

    @Mock
    private Pageable mockPageable;

    @Mock
    private Page<Article> mockArticlePages;

    @InjectMocks
    private ArticleSearchInternalService articleSearchInternalService;

    @Test
    void 페이지_개수가_20개가_아니면_에러가_발생한다() {
        when(mockPageable.getPageSize()).thenReturn(19);

        assertThrows(InvalidPageRequstException.class, () -> articleSearchInternalService.findAllByTitle("test", mockPageable));
    }

    @Test
    void 정상적으로_제목이_포함된_게시글을_찾는다() {
        when(mockPageable.getPageSize()).thenReturn(20);
        when(mockArticleRepository.findAllByArticleFormTitleContaining(anyString(), any())).thenReturn(mockArticlePages);

        assertDoesNotThrow(() -> articleSearchInternalService.findAllByTitle("test", mockPageable));
    }
}