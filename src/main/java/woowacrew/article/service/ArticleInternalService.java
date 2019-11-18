package woowacrew.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleConverter;
import woowacrew.article.domain.ArticleDto;
import woowacrew.article.domain.ArticleRepository;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserDto;
import woowacrew.user.service.UserInternalService;

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

    public Article save(ArticleDto articleDto, UserDto userDto) {
        User user = userInternalService.findByUserId(userDto.getUserId());
        Article article = ArticleConverter.articleDtoToArticle(articleDto, user);
        articleRepository.save(article);
        return article;
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 게시글을 찾을 수 없습니다."));
    }
}
