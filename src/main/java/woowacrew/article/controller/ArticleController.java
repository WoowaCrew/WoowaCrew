package woowacrew.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woowacrew.article.domain.ArticleDto;
import woowacrew.article.domain.ArticleResponse;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpSession;
import java.net.URI;

@Controller
public class ArticleController {
    public static final String ARTICLES_URL = "/articles/";
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/new")
    public String articleForm() {
        return "article-edit";
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(HttpSession session, ArticleDto articleDto) {
        UserDto user = (UserDto) session.getAttribute("user");
        ArticleResponse articleResponse = articleService.save(articleDto, user);
        return ResponseEntity.created(URI.create(ARTICLES_URL + articleResponse.getId())).body(articleResponse);
    }
}
