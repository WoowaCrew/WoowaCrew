package woowacrew.keyword.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import woowacrew.keyword.domain.Keyword;
import woowacrew.keyword.service.KeywordService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private static final String REDIRECT_GOOGLE_SEARCH_URL = "redirect:https://www.google.com/search?q=";
    private static final String UTF_8 = "UTF-8";

    private KeywordService keywordService;

    @Autowired
    public SearchController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String content) throws UnsupportedEncodingException {
        long keywordId = keywordService.save(content);
        logger.debug("Google search : {}, Keyword Id : {}", content, keywordId);

        return REDIRECT_GOOGLE_SEARCH_URL + URLEncoder.encode(content, UTF_8);
    }

    @GetMapping("/search/rank")
    public ModelAndView searchRank() {
        ModelAndView modelAndView = new ModelAndView("keywordRank");

        List<Keyword> keywordRank = keywordService.keywordRank();
        modelAndView.addObject("keywordRank", keywordRank);

        return modelAndView;
    }
}
