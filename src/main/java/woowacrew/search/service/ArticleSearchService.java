package woowacrew.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.utils.ArticleConverter;
import woowacrew.search.domain.ArticleSearchSpec;

import java.util.List;

@Service
public class ArticleSearchService {

    private ArticleSearchInternalService articleSearchInternalService;

    public ArticleSearchService(ArticleSearchInternalService articleSearchInternalService) {
        this.articleSearchInternalService = articleSearchInternalService;
    }

    public ArticleResponseDtos findAll(String searchType, String content, Pageable pageable) {
        Specification<Article> specification = ArticleSearchSpec.getSpecification(searchType, content);
        Page<Article> articlePages = articleSearchInternalService.findAll(specification, pageable);
        List<ArticleResponseDto> articleResponseDtos = ArticleConverter.articlePagesToArticleResponseDtos(articlePages);

        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }
}
