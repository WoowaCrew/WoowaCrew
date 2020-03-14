package woowacrew.article.free.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.utils.ArticleConverter;
import woowacrew.user.dto.UserContext;

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
        return ArticleConverter.toDto(article);
    }

    public ArticleResponseDto findById(Long articleId) {
        return ArticleConverter.toDto(articleInternalService.findById(articleId));
    }

    public ArticleResponseDtos findAll(Pageable pageable) {
        Page<Article> articlePages = articleInternalService.findAll(pageable);
        List<ArticleResponseDto> articleResponseDtos = articlePages.stream()
                .map(ArticleConverter::toDto)
                .collect(Collectors.toList());
        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }

    public ArticleResponseDto update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        return ArticleConverter.toDto(articleInternalService.update(articleUpdateDto, userContext));
    }

    public void delete(Long articleId, UserContext userContext) {
        articleInternalService.delete(articleId, userContext);
    }

    public ArticleResponseDtos findSearchedArticles(Specification<Article> specification, Pageable pageable) {
        Page<Article> articlePages = articleInternalService.findAll(specification, pageable);
        List<ArticleResponseDto> articleResponseDtos = ArticleConverter.toDtos(articlePages);

        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }
}
