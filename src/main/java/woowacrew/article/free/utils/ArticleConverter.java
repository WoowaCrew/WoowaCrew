package woowacrew.article.free.utils;

import org.springframework.data.domain.Page;
import woowacrew.article.crew.domain.CrewArticle;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.dto.ArticleRequestDto;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.user.domain.User;
import woowacrew.user.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleConverter {
    private ArticleConverter() {
    }

    public static ArticleResponseDto toDto(Article article) {
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(),
                UserConverter.toDto(article.getAuthor()), article.getCreatedDate(), article.getLastModifiedDate());
    }

    public static ArticleResponseDto toDto(CrewArticle article) {
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(),
                UserConverter.toDto(article.getAuthor()), article.getCreatedDate(), article.getLastModifiedDate());
    }

    public static List<ArticleResponseDto> toDtos(Page<Article> articlePages) {
        return articlePages.stream()
                .map(ArticleConverter::toDto)
                .collect(Collectors.toList());
    }

    public static Article toEntity(ArticleRequestDto articleRequestDto, User user) {
        return new Article(articleRequestDto.getTitle(), articleRequestDto.getContent(), user);
    }
}
