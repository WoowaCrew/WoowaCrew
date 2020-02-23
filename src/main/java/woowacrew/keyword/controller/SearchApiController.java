package woowacrew.keyword.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacrew.keyword.domain.KeywordConverter;
import woowacrew.keyword.domain.KeywordRequestDto;
import woowacrew.keyword.domain.KeywordResponseDto;
import woowacrew.keyword.service.KeywordService;

import java.io.UnsupportedEncodingException;
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

    @PostMapping
    public ResponseEntity<String> search(KeywordRequestDto keywordRequestDto) throws UnsupportedEncodingException {
        KeywordResponseDto keywordResponseDto = keywordService.save(keywordRequestDto);
        logger.debug("Google search : {}, Keyword Id : {}", keywordResponseDto.getContent(), keywordResponseDto.getId());

        return ResponseEntity.ok(KeywordConverter.toRedirectUri(keywordResponseDto.getContent()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> increaseViews(@PathVariable Long id) throws UnsupportedEncodingException {
        KeywordResponseDto keywordResponseDto = keywordService.increaseViews(id);
        logger.debug("Success keyword views to increase : {}", keywordResponseDto.getContent());

        return ResponseEntity.ok(KeywordConverter.toRedirectUri(keywordResponseDto.getContent()));
    }
}
