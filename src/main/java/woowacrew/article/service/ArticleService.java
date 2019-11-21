package woowacrew.article.service;

import org.springframework.stereotype.Service;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleConverter;
import woowacrew.article.domain.ArticleDto;
import woowacrew.article.domain.ArticleResponse;
import woowacrew.user.domain.UserDto;

@Service
public class ArticleService {
    private ArticleInternalService articleInternalService;

    public ArticleService(ArticleInternalService articleInternalService) {
        this.articleInternalService = articleInternalService;
    }

    public ArticleResponse save(ArticleDto articleDto, UserDto userDto) {
        Article article = articleInternalService.save(articleDto, userDto);
        return ArticleConverter.articleToArticleResponseDto(article);
    }

    public ArticleResponse findById(Long articleId) {
        return ArticleConverter.articleToArticleResponseDto(articleInternalService.findById(articleId));
    }
}
