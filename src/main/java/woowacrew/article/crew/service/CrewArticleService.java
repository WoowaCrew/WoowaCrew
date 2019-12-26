package woowacrew.article.crew.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.article.crew.domain.CrewArticle;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.dto.ArticleUpdateDto;
import woowacrew.article.free.utils.ArticleConverter;
import woowacrew.search.domain.SearchSpec;
import woowacrew.user.dto.UserContext;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrewArticleService {
    private CrewArticleInternalService crewArticleInternalService;

    public CrewArticleService(CrewArticleInternalService articleInternalService) {
        this.crewArticleInternalService = articleInternalService;
    }

    public ArticleResponseDto save(ArticleRequestDto articleRequestDto, UserContext userContext) {
        CrewArticle article = crewArticleInternalService.save(articleRequestDto, userContext);
        return ArticleConverter.toDto(article);
    }

    public ArticleResponseDto findById(Long articleId, UserContext userContext) {
        return ArticleConverter.toDto(crewArticleInternalService.findById(articleId, userContext));
    }

    public ArticleResponseDtos findAllByCrew(UserContext userContext, Pageable pageable) {
        Page<CrewArticle> articlePages = crewArticleInternalService.findAllByCrew(userContext, pageable);
        List<ArticleResponseDto> articleResponseDtos = articlePages.stream()
                .map(ArticleConverter::toDto)
                .collect(Collectors.toList());
        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }

    public ArticleResponseDto update(ArticleUpdateDto articleUpdateDto, UserContext userContext) {
        return ArticleConverter.toDto(crewArticleInternalService.update(articleUpdateDto, userContext));
    }

    public void delete(Long articleId, UserContext userContext) {
        crewArticleInternalService.delete(articleId, userContext);
    }

    public ArticleResponseDtos findSearchedArticles(SearchSpec<CrewArticle> searchSpec, Pageable pageable, UserContext userContext) {
        Page<CrewArticle> articlePages = crewArticleInternalService.findSearchedArticles(searchSpec, pageable, userContext);
        List<ArticleResponseDto> articleResponseDtos = articlePages.stream()
                .map(ArticleConverter::toDto)
                .collect(Collectors.toList());
        return new ArticleResponseDtos(pageable.getPageNumber(), articlePages.getTotalPages(), articleResponseDtos);
    }
}
