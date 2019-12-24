package woowacrew.article.crew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import woowacrew.article.crew.service.CrewArticleInternalService;
import woowacrew.article.crew.service.CrewArticleService;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.user.dto.UserContext;

@Controller
public class CrewArticleController {
    private final CrewArticleService crewArticleService;
    private final CrewArticleInternalService crewArticleInternalService;

    public CrewArticleController(CrewArticleService crewArticleService, CrewArticleInternalService crewArticleInternalService) {
        this.crewArticleService = crewArticleService;
        this.crewArticleInternalService = crewArticleInternalService;
    }

    @GetMapping("/article/crew/new")
    public String articleForm() {
        return "article-crew-create";
    }

    @GetMapping("/articles/crew")
    public String articles() {
        return "article-crew-list";
    }

    @GetMapping("/articles/crew/{articleId}")
    public String showArticle(@PathVariable Long articleId, UserContext userContext, Model model) {
        ArticleResponseDto articleResponseDto = crewArticleService.findById(articleId, userContext);
        model.addAttribute("article", articleResponseDto);
        return "article-crew";
    }

    @GetMapping("/articles/crew/{articleId}/edit")
    public String articleEditForm(@PathVariable Long articleId, UserContext userContext) {
        crewArticleInternalService.checkAuthor(articleId, userContext);
        return "article-crew-edit";
    }
}
