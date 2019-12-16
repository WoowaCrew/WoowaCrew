package woowacrew.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.exception.InvalidPageRequstException;

@Service
@Transactional(readOnly = true)
public class ArticleSearchInternalService {

    private static final int DEFAULT_ARTICLE_PAGE_SIZE = 20;

    private ArticleRepository articleRepository;

    public ArticleSearchInternalService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<Article> findAll(Specification<Article> specification, Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        return articleRepository.findAll(specification, pageable);
    }
}
