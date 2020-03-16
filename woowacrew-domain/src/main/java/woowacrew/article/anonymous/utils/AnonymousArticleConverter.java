package woowacrew.article.anonymous.utils;

import org.springframework.data.domain.Page;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<AnonymousArticleResponseDto> toDtos(Page<AnonymousArticle> anonymousArticlePages) {
        return anonymousArticlePages.stream()
                .map(AnonymousArticleConverter::toDto)
                .collect(Collectors.toList());
    }
}
