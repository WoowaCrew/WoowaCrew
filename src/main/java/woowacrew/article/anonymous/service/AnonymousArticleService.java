package woowacrew.article.anonymous.service;

import org.springframework.stereotype.Service;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnonymousArticleService {
    private final AnonymousArticleInternalService anonymousArticleInternalService;

    public AnonymousArticleService(AnonymousArticleInternalService anonymousArticleInternalService) {
        this.anonymousArticleInternalService = anonymousArticleInternalService;
    }

    public List<AnonymousArticleResponseDto> findApprovedAnonymousArticles() {
        return anonymousArticleInternalService.findByIsApproved(true)
                .stream()
                .map(AnonymousArticleConverter::toDto)
                .collect(Collectors.toList());
    }

    public AnonymousArticleResponseDto save(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticle anonymousArticle = anonymousArticleInternalService.save(anonymousArticleRequestDto);
        return AnonymousArticleConverter.toDto(anonymousArticle);
    }
}
