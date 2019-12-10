package woowacrew.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.service.ArticleInternalService;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.UserContext;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleInternalService articleInternalService;

    public ArticleController(ArticleService articleService, ArticleInternalService articleInternalService) {
        this.articleService = articleService;
        this.articleInternalService = articleInternalService;
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

    @GetMapping("/articles/{articleId}/edit")
    public String articleEditForm(@PathVariable Long articleId, UserContext userContext) {
        articleInternalService.checkAuthor(articleId, userContext);
        return "article-edit";
    }
}
