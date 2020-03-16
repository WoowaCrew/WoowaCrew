package woowacrew.article.free.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.domain.ArticleRepository;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.article.free.exception.NotFoundArticleException;
import woowacrew.article.free.utils.ArticleConverter;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

@Service
@Transactional
public class ArticleInternalService {
    public static final int DEFAULT_ARTICLE_PAGE_SIZE = 20;
    private ArticleRepository articleRepository;
    private UserInternalService userInternalService;

    @Autowired
    public ArticleInternalService(ArticleRepository articleRepository, UserInternalService userInternalService) {
        this.articleRepository = articleRepository;
        this.userInternalService = userInternalService;
    }

    public Article save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        User user = userInternalService.findByOauthId(userContext.getOauthId());
        Article article = ArticleConverter.toEntity(articleRequestDto, user);
        articleRepository.save(article);
        return article;
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(NotFoundArticleException::new);
    }

    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        return articleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Article> findAll(Specification<Article> specification, Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        return articleRepository.findAll(specification, pageable);
    }

    public Article update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        Article article = findById(articleUpdateDto.getArticleId());

        article.update(user, articleUpdateDto.getTitle(), articleUpdateDto.getContent());

        return article;
    }

    public void delete(Long articleId, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        Article article = findById(articleId);

        article.checkAuthor(user);

        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public void checkAuthor(Long articleId, UserContext userContext) {
        Article article = findById(articleId);
        User user = userInternalService.findById(userContext.getId());

        article.checkAuthor(user);
    }
}
