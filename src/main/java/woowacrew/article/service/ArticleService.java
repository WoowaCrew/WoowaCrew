package woowacrew.article.service;

import org.springframework.stereotype.Service;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleConverter;
import woowacrew.article.domain.ArticleRequestDto;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.user.domain.UserContext;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private ArticleInternalService articleInternalService;

    public ArticleService(ArticleInternalService articleInternalService) {
        this.articleInternalService = articleInternalService;
    }

    public ArticleResponseDto save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        Article article = articleInternalService.save(articleRequestDto, userContext);
        return ArticleConverter.articleToArticleResponseDto(article);
    }

    public ArticleResponseDto findById(Long articleId) {
        return ArticleConverter.articleToArticleResponseDto(articleInternalService.findById(articleId));
    }

    public List<ArticleResponseDto> findAll() {
        return articleInternalService.findAll().stream()
                .map(ArticleConverter::articleToArticleResponseDto)
                .collect(Collectors.toList());
    }
}
