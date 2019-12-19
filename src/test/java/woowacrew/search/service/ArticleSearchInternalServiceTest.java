package woowacrew.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.exception.InvalidPageRequstException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleSearchInternalServiceTest {

    @Mock
    private Specification<Article> mockArticleSpecification;

    @Mock
    private ArticleRepository mockArticleRepository;

    @Mock
    private Page<Article> mockArticlePages;

    @InjectMocks
    private ArticleSearchInternalService articleSearchInternalService;

    @Test
    void 페이지_개수가_20개가_아니면_에러가_발생한다() {
        Pageable differentPageable = PageRequest.of(0, 19);
        assertThrows(InvalidPageRequstException.class, () -> articleSearchInternalService.findAll(mockArticleSpecification, differentPageable));
    }

    @Test
    void 정상적으로_제목이_포함된_게시글을_찾는다() {
        Pageable pageable = PageRequest.of(0, 20);
        when(mockArticleRepository.findAll(mockArticleSpecification, pageable)).thenReturn(mockArticlePages);

        assertDoesNotThrow(() -> articleSearchInternalService.findAll(mockArticleSpecification, pageable));
    }
}