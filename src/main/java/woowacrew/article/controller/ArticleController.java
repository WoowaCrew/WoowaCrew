package woowacrew.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woowacrew.article.domain.ArticleDto;
import woowacrew.article.domain.ArticleResponse;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponse);
    }
}
