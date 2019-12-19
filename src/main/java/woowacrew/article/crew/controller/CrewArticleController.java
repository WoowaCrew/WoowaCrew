package woowacrew.article.crew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import woowacrew.article.crew.service.CrewArticleInternalService;
import woowacrew.article.crew.service.CrewArticleService;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.user.dto.UserContext;

@Controller
@RequestMapping("/article/crew")
public class CrewArticleController {
    private final CrewArticleService crewArticleService;
    private final CrewArticleInternalService crewArticleInternalService;

    public CrewArticleController(CrewArticleService crewArticleService, CrewArticleInternalService crewArticleInternalService) {
        this.crewArticleService = crewArticleService;
        this.crewArticleInternalService = crewArticleInternalService;
    }

    @GetMapping("/new")
    public String articleForm() {
        return "article-crew-create";
    }

    @GetMapping
    public String articles() {
        return "article-crew-list";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable Long articleId, UserContext userContext, Model model) {
        ArticleResponseDto articleResponseDto = crewArticleService.findById(articleId, userContext);
        model.addAttribute("article", articleResponseDto);
        return "article-crew";
    }

    @GetMapping("/{articleId}/edit")
    public String articleEditForm(@PathVariable Long articleId, UserContext userContext) {
        crewArticleInternalService.checkAuthor(articleId, userContext);
        return "article-crew-edit";
    }
}
