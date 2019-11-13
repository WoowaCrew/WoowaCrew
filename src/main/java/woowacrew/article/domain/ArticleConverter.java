package woowacrew.article.domain;

import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;

public class ArticleConverter {
    public static ArticleResponse articleToArticleResponseDto(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),
                UserConverter.userToUserDto(article.getUser()), article.getCreatedDate(), article.getLastModifiedDate());
    }

    public static Article articleDtoToArticle(ArticleDto articleDto, User user) {
        return new Article(articleDto.getTitle(), articleDto.getContent(), user);
    }
}
