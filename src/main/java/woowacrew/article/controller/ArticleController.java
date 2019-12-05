package woowacrew.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.UserContext;
import woowacrew.utils.annotation.AuthenticationUser;

@Controller
public class ArticleController {
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/new")
    public String articleForm() {
        return "article-create";
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

    @GetMapping("/articles/edit/{articleId}")
    public String articleEditForm(@AuthenticationUser UserContext userContext) {
        return "article-edit";
    }

}
