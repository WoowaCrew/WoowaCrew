package woowacrew.article.domain;

import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;

public class ArticleConverter {
    public static ArticleResponseDto articleToArticleResponseDto(Article article) {
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(),
                UserConverter.userToUserResponseDto(article.getUser()), article.getCreatedDate(), article.getLastModifiedDate());
    }

    public static Article articleDtoToArticle(ArticleRequestDto articleRequestDto, User user) {
        return new Article(articleRequestDto.getTitle(), articleRequestDto.getContent(), user);
    }
}
