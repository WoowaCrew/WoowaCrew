package woowacrew.article.anonymous.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.service.AnonymousArticleInternalService;
import woowacrew.article.anonymous.service.AnonymousArticleService;

import java.net.URI;

@RestController
@RequestMapping("/api/articles/anonymous")
public class AnonymousArticleController {

    private final AnonymousArticleService anonymousArticleService;
    private final AnonymousArticleInternalService anonymousArticleInternalService;

    public AnonymousArticleController(AnonymousArticleService anonymousArticleService,
                                      AnonymousArticleInternalService anonymousArticleInternalService) {
        this.anonymousArticleService = anonymousArticleService;
        this.anonymousArticleInternalService = anonymousArticleInternalService;
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