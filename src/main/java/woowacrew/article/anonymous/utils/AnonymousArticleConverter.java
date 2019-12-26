package woowacrew.article.anonymous.utils;

import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;

public class AnonymousArticleConverter {
    private AnonymousArticleConverter() {
    }

    public static AnonymousArticle toEntity(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        return new AnonymousArticle(
                anonymousArticleRequestDto.getTitle(),
                anonymousArticleRequestDto.getContent(),
                anonymousArticleRequestDto.getSigningKey());
    }

    public static AnonymousArticleResponseDto toDto(AnonymousArticle anonymousArticle) {
        return new AnonymousArticleResponseDto(
                anonymousArticle.getId(),
                anonymousArticle.getTitle(),
                anonymousArticle.getContent(),
                anonymousArticle.isApproved(),
                anonymousArticle.getCreatedDate(),
                anonymousArticle.getLastModifiedDate());
    }
}
