package woowacrew.article.service;

import org.springframework.stereotype.Service;
import woowacrew.article.domain.*;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserDto;
import woowacrew.user.service.UserInternalService;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    private UserInternalService userInternalService;

    public ArticleService(ArticleRepository articleRepository, UserInternalService userInternalService) {
        this.articleRepository = articleRepository;
        this.userInternalService = userInternalService;
    }

    public ArticleResponse save(ArticleDto articleDto, UserDto userDto) {
        User user = userInternalService.findByUserId(userDto.getUserId());
        Article article = ArticleConverter.articleDtoToArticle(articleDto, user);
        articleRepository.save(article);
        return ArticleConverter.articleToArticleResponseDto(article);
    }
}
