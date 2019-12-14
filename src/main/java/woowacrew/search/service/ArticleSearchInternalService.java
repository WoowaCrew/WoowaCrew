package woowacrew.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.article.free.service.ArticleInternalService;

@Service
@Transactional(readOnly = true)
public class ArticleSearchInternalService {

    private ArticleRepository articleRepository;

    public ArticleSearchInternalService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<Article> findAllByTitle(String content, Pageable pageable) {
        if (pageable.getPageSize() != ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        return articleRepository.findAllByArticleFormTitleContaining(content, pageable);
    }
}
