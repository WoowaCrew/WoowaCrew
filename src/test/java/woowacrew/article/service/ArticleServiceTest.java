package woowacrew.article.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.domain.ArticleDto;
import woowacrew.article.domain.ArticleRepository;
import woowacrew.article.domain.ArticleResponse;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserDto;
import woowacrew.user.service.UserInternalService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void 게시글_생성_테스트() {
        UserDto userDto = new UserDto("asd", "asd");
        User user = new User("asd", "asd");
        ArticleDto articleDto = new ArticleDto("hello", "bonjour");

        when(userInternalService.findByUserId(userDto.getUserId())).thenReturn(user);

        ArticleResponse articleResponse = articleService.save(articleDto, userDto);

        assertThat(articleResponse.getTitle()).isEqualTo("hello");
        assertThat(articleResponse.getContent()).isEqualTo("bonjour");
    }
}