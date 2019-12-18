package woowacrew.article.anonymous.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.anonymous.domain.AnonymousArticle;
import woowacrew.article.anonymous.domain.AnonymousArticleRepository;
import woowacrew.article.anonymous.dto.AnonymousArticleRequestDto;
import woowacrew.article.anonymous.utils.AnonymousArticleConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnonymousArticleInternalServiceTest {

    @Mock
    private AnonymousArticleRepository anonymousArticleRepository;

    @InjectMocks
    private AnonymousArticleInternalService anonymousArticleInternalService;

    @Test
    @DisplayName("익명 게시글 저장")
    void save() {
        String title = "title";
        String content = "content";
        String password = "password";
        AnonymousArticleRequestDto anonymousArticleRequestDto
                = new AnonymousArticleRequestDto(title, content, password);
        AnonymousArticle anonymousArticle =
                AnonymousArticleConverter.anonymousArticleRequestDtoToArticle(anonymousArticleRequestDto);

        when(anonymousArticleRepository.save(any(AnonymousArticle.class))).thenReturn(anonymousArticle);

        AnonymousArticle savedAnonymousArticle
                = anonymousArticleInternalService.save(anonymousArticleRequestDto);

        assertThat(savedAnonymousArticle.getTitle()).isEqualTo(title);
        assertThat(savedAnonymousArticle.getContent()).isEqualTo(content);
    }
}