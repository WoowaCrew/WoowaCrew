package woowacrew.keyword.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.keyword.domain.KeywordRequestDto;
import woowacrew.keyword.domain.KeywordResponseDto;
import woowacrew.keyword.service.KeywordService;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

    private static final Logger logger = LoggerFactory.getLogger(SearchApiController.class);

    private KeywordService keywordService;

    public SearchApiController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/rank")
    public ResponseEntity<List<KeywordResponseDto>> searchRank() {
        List<KeywordResponseDto> keywordResponseDtos = keywordService.keywordRank();
        return ResponseEntity.ok(keywordResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> increaseViews(@PathVariable Long id) {
        KeywordResponseDto keywordResponseDto = keywordService.increaseViews(id);
        logger.info("Success keyword views to increase : {}", keywordResponseDto.getContent());

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> search(KeywordRequestDto keywordRequestDto) {
        KeywordResponseDto keywordResponseDto = keywordService.save(keywordRequestDto);
        logger.info("Google search : {}, Keyword Id : {}", keywordResponseDto.getContent(), keywordResponseDto.getId());

        return ResponseEntity.ok().build();
    }
}
