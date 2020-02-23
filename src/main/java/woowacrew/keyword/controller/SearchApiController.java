package woowacrew.keyword.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.keyword.domain.KeywordConverter;
import woowacrew.keyword.domain.KeywordRequestDto;
import woowacrew.keyword.domain.KeywordResponseDto;
import woowacrew.keyword.service.KeywordService;

import java.io.UnsupportedEncodingException;

@RestController
public class SearchApiController {
    private static final Logger logger = LoggerFactory.getLogger(SearchApiController.class);

    private KeywordService keywordService;

    public SearchApiController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping("/api/search")
    public ResponseEntity<String> search(KeywordRequestDto keywordRequestDto) throws UnsupportedEncodingException {
        KeywordResponseDto keywordResponseDto = keywordService.save(keywordRequestDto);
        logger.debug("Google search : {}, Keyword Id : {}", keywordResponseDto.getContent(), keywordResponseDto.getId());

        String redirectUri = KeywordConverter.toRedirectUri(keywordResponseDto.getContent());
        return ResponseEntity.ok(redirectUri);
    }
}
