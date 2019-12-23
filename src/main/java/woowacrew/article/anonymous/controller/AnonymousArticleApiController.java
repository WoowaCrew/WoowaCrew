package woowacrew.article.anonymous.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.article.anonymous.dto.AnonymousArticleUpdateDto;
import woowacrew.article.anonymous.service.AnonymousArticleInternalService;
import woowacrew.article.anonymous.service.AnonymousArticleService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/articles/anonymous")
public class AnonymousArticleApiController {

    private final AnonymousArticleService anonymousArticleService;

    public AnonymousArticleApiController(AnonymousArticleService anonymousArticleService) {
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

    @GetMapping("/unapproved")
    public ResponseEntity<List<AnonymousArticleResponseDto>> unapprovedList() {
        return ResponseEntity.ok(anonymousArticleService.findUnapprovedAnonymousArticles());
    }

    @GetMapping("/{anonymousArticleId}")
    public ResponseEntity<AnonymousArticleResponseDto> show(@PathVariable Long anonymousArticleId) {
        return ResponseEntity.ok(anonymousArticleService.findById(anonymousArticleId));
    }

    @PostMapping
    public ResponseEntity<AnonymousArticleResponseDto> create(AnonymousArticleRequestDto anonymousArticleRequestDto) {
        AnonymousArticleResponseDto anonymousArticleResponseDto =
                anonymousArticleService.save(anonymousArticleRequestDto);
        return ResponseEntity
                .created(URI.create("/api/articles/anonymous/" + anonymousArticleResponseDto.getAnonymousArticleId()))
                .body(anonymousArticleResponseDto);
    }

    @PostMapping("/{anonymousArticleId}/check")
    public ResponseEntity checkPassword(@PathVariable Long anonymousArticleId, String password) {
        anonymousArticleService.check(anonymousArticleId, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{anonymousArticleId}")
    public ResponseEntity<AnonymousArticleResponseDto> update(@PathVariable Long anonymousArticleId,
                                                              AnonymousArticleUpdateDto anonymousArticleUpdateDto) {
        return ResponseEntity.ok(anonymousArticleService.update(anonymousArticleId, anonymousArticleUpdateDto));
    }

    @PutMapping("/{anonymousArticleId}/approve")
    public ResponseEntity<AnonymousArticleResponseDto> approve(@PathVariable Long anonymousArticleId) {
        return ResponseEntity.ok(anonymousArticleService.approve(anonymousArticleId));
    }

    @PutMapping("/{anonymousArticleId}/delete")
    public ResponseEntity<AnonymousArticleResponseDto> delete(@PathVariable Long anonymousArticleId, String password) {
        anonymousArticleService.delete(anonymousArticleId, password);
        return ResponseEntity.ok().build();
    }
}