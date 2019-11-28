package woowacrew.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.service.ArticleService;

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

    @GetMapping("/articles")
    public String articles() {
        return "article-list";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(@PathVariable Long articleId, Model model) {
        ArticleResponseDto articleResponseDto = articleService.findById(articleId);
        model.addAttribute("article", articleResponseDto);
        return "article";
    }
}
