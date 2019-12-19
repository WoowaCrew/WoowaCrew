package woowacrew.article.anonymous.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles/anonymous")
public class AnonymousArticleController {

    @GetMapping
    public String list() {
        return "anonymous-article-list";
    }

    @GetMapping("/new")
    public String anonymousArticleForm() {
        return "anonymous-article-create";
    }
}
