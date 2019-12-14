package woowacrew.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.utils.ArticleConverter;

import java.util.List;

@Service
public class ArticleSearchService {

    private ArticleSearchInternalService articleSearchInternalService;

    public ArticleSearchService(ArticleSearchInternalService articleSearchInternalService) {
        this.articleSearchInternalService = articleSearchInternalService;
    }

    public ArticleResponseDtos findAllByTitle(String content, Pageable pageable) {
        Page<Article> articlePages = articleSearchInternalService.findAllByTitle(content, pageable);

        List<ArticleResponseDto> articleResponseDtos = ArticleConverter.articlePagesToArticleResponseDtos(articlePages);

        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }
}
