package woowacrew.article.service;

import org.springframework.stereotype.Service;
import woowacrew.article.domain.*;
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

    public ArticleResponseDto update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        return ArticleConverter.articleToArticleResponseDto(articleInternalService.update(articleUpdateDto, userContext));
    }

    public void delete(Long articleId, UserContext userContext) {
        articleInternalService.delete(articleId, userContext);
    }
}
