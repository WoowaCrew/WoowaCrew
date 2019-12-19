package woowacrew.article.anonymous.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.article.anonymous.service.AnonymousArticleInternalService;
import woowacrew.article.anonymous.service.AnonymousArticleService;

import java.net.URI;

@RestController
@RequestMapping("/api/articles/anonymous")
public class AnonymousArticleController {

    private final AnonymousArticleService anonymousArticleService;

    public AnonymousArticleController(AnonymousArticleService anonymousArticleService) {
        this.anonymousArticleService = anonymousArticleService;
    }

    @GetMapping("/approved")
    public ResponseEntity<AnonymousArticleResponseDtos> approvedList(
            @PageableDefault(
                    size = AnonymousArticleInternalService.DEFAULT_ANONYMOUS_ARTICLE_PAGE_SIZE,
                    sort = "id",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        return ResponseEntity.ok(anonymousArticleService.findApprovedAnonymousArticles(pageable));
    }

    @PostMapping
    public ResponseEntity<AnonymousArticleResponseDto> create(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticleResponseDto anonymousArticleResponseDto =
                anonymousArticleService.save(anonymousArticleRequestDto);
        return ResponseEntity
                .created(URI.create("/api/articles/anonymous/" + anonymousArticleResponseDto.getAnonymousArticleId()))
                .body(anonymousArticleResponseDto);
    }
}