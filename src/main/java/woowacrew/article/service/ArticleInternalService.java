package woowacrew.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleConverter;
import woowacrew.article.domain.ArticleRepository;
import woowacrew.article.domain.ArticleRequestDto;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.List;

@Service
@Transactional
public class ArticleInternalService {
    private ArticleRepository articleRepository;
    private UserInternalService userInternalService;

    @Autowired
    public ArticleInternalService(ArticleRepository articleRepository, UserInternalService userInternalService) {
        this.articleRepository = articleRepository;
        this.userInternalService = userInternalService;
    }

    public Article save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        User user = userInternalService.findByOauthId(userContext.getOauthId());
        Article article = ArticleConverter.articleDtoToArticle(articleRequestDto, user);
        articleRepository.save(article);
        return article;
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAllByOrderByIdDesc();
    }
}
