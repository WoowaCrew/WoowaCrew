package woowacrew.keyword.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.keyword.service.KeywordService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class SearchApiController {
    private static final Logger logger = LoggerFactory.getLogger(SearchApiController.class);
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    private static final String UTF_8 = "UTF-8";

    private KeywordService keywordService;

    @Autowired
    public SearchApiController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/search")
    public void search(String content, HttpServletResponse response) throws UnsupportedEncodingException {
        long keywordId = keywordService.save(content);
        String url = GOOGLE_SEARCH_URL + URLEncoder.encode(content, UTF_8);
        logger.debug("Google search : {}, Keyword Id : {}", content, keywordId);

        response.setHeader("Location", url);
    }
}
