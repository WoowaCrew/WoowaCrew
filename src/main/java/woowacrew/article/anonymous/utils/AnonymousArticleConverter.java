package woowacrew.article.anonymous.utils;

import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;

public class AnonymousArticleConverter {
    public static AnonymousArticle anonymousArticleRequestDtoToArticle(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        return new AnonymousArticle(
                anonymousArticleRequestDto.getTitle(),
                anonymousArticleRequestDto.getContent(),
                anonymousArticleRequestDto.getPassword());
    }
}
