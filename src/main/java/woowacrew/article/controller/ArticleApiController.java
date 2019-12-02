package woowacrew.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.article.domain.ArticleRequestDto;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.article.service.ArticleService;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;
import woowacrew.user.domain.UserDto;
import woowacrew.utils.annotation.AuthenticationUser;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@RestController
public class ArticleApiController {
    public static final String ARTICLES_URL = "/articles/";

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponseDto>> list() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/api/articles/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.findById(articleId));
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponseDto> createArticle(@AuthenticationUser User user, ArticleRequestDto articleRequestDto) {
        UserDto userDto = UserConverter.userToUserDto(user);
        ArticleResponseDto articleResponseDto = articleService.save(articleRequestDto, userDto);
        return ResponseEntity.created(URI.create(ARTICLES_URL + articleResponseDto.getId())).body(articleResponseDto);
    }
}
