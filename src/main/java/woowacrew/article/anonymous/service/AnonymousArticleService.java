package woowacrew.article.anonymous.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnonymousArticleService {
    private final AnonymousArticleInternalService anonymousArticleInternalService;

    public AnonymousArticleService(AnonymousArticleInternalService anonymousArticleInternalService) {
        this.anonymousArticleInternalService = anonymousArticleInternalService;
    }

    public AnonymousArticleResponseDto save(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticle anonymousArticle = anonymousArticleInternalService.save(anonymousArticleRequestDto);
        return AnonymousArticleConverter.toDto(anonymousArticle);
    }

    public AnonymousArticleResponseDto findById(Long anonymousArticleId) {
        AnonymousArticle anonymousArticle = anonymousArticleInternalService.findById(anonymousArticleId);
        return AnonymousArticleConverter.toDto(anonymousArticle);
    }

    public AnonymousArticleResponseDtos findApprovedAnonymousArticles(Pageable pageable) {
        Page<AnonymousArticle> approvedAnonymousArticlePages =
                anonymousArticleInternalService.findByIsApproved(pageable, true);
        List<AnonymousArticleResponseDto> anonymousArticleResponseDtos =
                approvedAnonymousArticlePages.stream()
                        .map(AnonymousArticleConverter::toDto)
                        .collect(Collectors.toList());

        return new AnonymousArticleResponseDtos(
                pageable.getPageNumber(),
                approvedAnonymousArticlePages.getTotalPages(),
                anonymousArticleResponseDtos);
    }

    public List<AnonymousArticleResponseDto> findUnapprovedAnonymousArticles() {
        return anonymousArticleInternalService.findByIsApproved(false)
                .stream().map(AnonymousArticleConverter::toDto)
                .collect(Collectors.toList());
    }

    public AnonymousArticleResponseDto approve(Long anonymousArticleId) {
        return AnonymousArticleConverter.toDto(anonymousArticleInternalService.approve(anonymousArticleId));
    }

    public void delete(Long anonymousArticleId, String password) {
        anonymousArticleInternalService.delete(anonymousArticleId, password);
    }
}
